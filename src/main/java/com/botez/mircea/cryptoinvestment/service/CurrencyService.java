package com.botez.mircea.cryptoinvestment.service;

import com.botez.mircea.cryptoinvestment.mapper.CurrencyMapper;
import com.botez.mircea.cryptoinvestment.model.CurrencySpecification;
import com.botez.mircea.cryptoinvestment.model.dto.CSVCurrencyDto;
import com.botez.mircea.cryptoinvestment.model.dto.CurrencyDto;
import com.botez.mircea.cryptoinvestment.model.entity.Currency;
import com.botez.mircea.cryptoinvestment.model.repository.CurrencyRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public boolean upoadFile(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<CSVCurrencyDto> csvToBean = new CsvToBeanBuilder<CSVCurrencyDto>(reader)
                    .withType(CSVCurrencyDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CSVCurrencyDto> currencyDtos = csvToBean.parse();
            currencyDtos.forEach(currencyDto -> currencyRepository.save(currencyMapper.toCurrencyEntity(currencyDto)));
        } catch (Exception ex) {
            //do something
            //print message
            return false;
        }
        return true;
    }

    public List<CurrencyDto> getAllCurrencies() {
       return currencyRepository.findAll().stream()
                .map(cryptoEntity -> currencyMapper.toCurrencyDto(cryptoEntity)).collect(Collectors.toList());
    }

    public ResponseEntity<CurrencyDto> getOldestValueForACurrency(String symbol) {
        if(validateCurrency(symbol)) {
            return new ResponseEntity<>(currencyMapper.toCurrencyDto(currencyRepository.findOldestValue(symbol)), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<CurrencyDto> getNewestValueForACurrency(String symbol) {
        if(validateCurrency(symbol)) {
            return new ResponseEntity<>(currencyMapper.toCurrencyDto(currencyRepository.findNewestValue(symbol)), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<CurrencyDto> getMinValueForACurrency(String symbol) {
        if(validateCurrency(symbol)) {
            return new ResponseEntity<>(currencyMapper.toCurrencyDto(currencyRepository.findMinValue(symbol)), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<CurrencyDto> getMaxValueForACurrency(String symbol) {
        if(validateCurrency(symbol)) {
            return new ResponseEntity<>(currencyMapper.toCurrencyDto(currencyRepository.findMaxValue(symbol)), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public Map<String, Double> getRankForAllCurrencies() {
        Map<String, Double> ranges = new HashMap<>();
        List<String> currencies = currencyRepository.findAllCurrencies();
        currencies.forEach(symbol -> {
            double normalizedRange = (currencyRepository.findMaxValue(symbol).getPrice() - currencyRepository.findMinValue(symbol).getPrice()) / currencyRepository.findMinValue(symbol).getPrice();
            ranges.put(symbol, normalizedRange);
        });
        return ranges.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, HashMap::new));
    }

    public ResponseEntity<Double> getRankForPeriod(String startDate, String endDate, String symbol) {
        if(validateCurrency(symbol)) {
            if(StringUtils.isEmpty(endDate)) {
                List<Currency> currencies = currencyRepository.findAll(
                        CurrencySpecification.startDateEquals(Instant.parse(startDate).toEpochMilli())
                                .and(CurrencySpecification.symbolEquals(symbol)));
                Double normalizedValue = computeNormalizedValue(currencies);
                return new ResponseEntity<Double>(normalizedValue, HttpStatus.OK);
            } else {
                List<Currency> currencies = currencyRepository.findAll(
                        CurrencySpecification.timestampInBetween(Instant.parse(startDate).toEpochMilli(), Instant.parse(endDate).toEpochMilli())
                                .and(CurrencySpecification.symbolEquals(symbol)));
                Double normalizedValue = computeNormalizedValue(currencies);
                return new ResponseEntity<Double>(normalizedValue, HttpStatus.OK);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Double computeNormalizedValue(List<Currency> currencies) {
        Currency maxValueCurrency = currencies.stream().max(Comparator.comparing(Currency::getPrice)).get();
        Currency minValueCurrency = currencies.stream().min(Comparator.comparing(Currency::getPrice)).get();
        return (maxValueCurrency.getPrice() - minValueCurrency.getPrice())/ minValueCurrency.getPrice();
    }

    private boolean validateCurrency(String symbol) {
        return currencyRepository.findAllCurrencies().contains(symbol);
    }
}
