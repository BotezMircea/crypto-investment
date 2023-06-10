package com.botez.mircea.cryptoinvestment.model.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CSVCurrencyDto {
    @CsvBindByName
    private Long timestamp;
    @CsvBindByName
    private String symbol;
    @CsvBindByName
    private Double price;

    @Override
    public String toString() {
        return "Currency{" +
                "timestamp=" + timestamp +
                ", symbol='" + symbol + '\'' +
                ", price=" + price +
                '}';
    }
}
