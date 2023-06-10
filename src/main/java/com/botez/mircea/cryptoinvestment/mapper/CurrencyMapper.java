package com.botez.mircea.cryptoinvestment.mapper;

import com.botez.mircea.cryptoinvestment.model.dto.CSVCurrencyDto;
import com.botez.mircea.cryptoinvestment.model.dto.CurrencyDto;
import com.botez.mircea.cryptoinvestment.model.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring",
        imports = Instant.class)
public interface CurrencyMapper {

    @Mapping(target = "id", ignore = true)
    Currency toCurrencyEntity(CSVCurrencyDto csvCurrencyDto);

    @Mapping(target = "timestamp", expression = "java(Instant.ofEpochMilli(currencyEntity.getTimestamp()))")
    CurrencyDto toCurrencyDto(Currency currencyEntity);
}
