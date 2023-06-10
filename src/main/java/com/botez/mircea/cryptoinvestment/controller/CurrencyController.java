package com.botez.mircea.cryptoinvestment.controller;

import com.botez.mircea.cryptoinvestment.model.dto.CurrencyDto;
import com.botez.mircea.cryptoinvestment.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam MultipartFile file) {
        ResponseEntity response;

        if(file.isEmpty()) {
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            response = currencyService.upoadFile(file) ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }

    @GetMapping("/crypto")
    public List<CurrencyDto> getCrypto() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/oldest")
    public ResponseEntity<CurrencyDto> getOldestValue(@RequestParam String symbol) {
        return currencyService.getOldestValueForACurrency(symbol);
    }

    @GetMapping("/newest")
    public ResponseEntity<CurrencyDto> getNewestValue(@RequestParam String symbol) {
        return currencyService.getNewestValueForACurrency(symbol);
    }

    @GetMapping("/min")
    public ResponseEntity<CurrencyDto> getMinValue(@RequestParam String symbol) {
        return currencyService.getMinValueForACurrency(symbol);
    }

    @GetMapping("/max")
    public ResponseEntity<CurrencyDto> getMaxValue(@RequestParam String symbol) {
        return currencyService.getMaxValueForACurrency(symbol);
    }

    @GetMapping("/rank")
    public Map<String, Double> getRankForAllCurrencies() {
        return currencyService.getRankForAllCurrencies();
    }

    @GetMapping("/rankForPeriod")
    public ResponseEntity<Double> getRankForPeriod(@RequestParam String startDate,
                                   @RequestParam(required = false) String endDate,
                                   @RequestParam String symbol) {
        return currencyService.getRankForPeriod(startDate, endDate, symbol);
    }
}
