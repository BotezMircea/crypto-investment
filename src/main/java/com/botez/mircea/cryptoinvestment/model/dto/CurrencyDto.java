package com.botez.mircea.cryptoinvestment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class CurrencyDto {

    private Instant timestamp;
    private String symbol;
    private Double price;
}
