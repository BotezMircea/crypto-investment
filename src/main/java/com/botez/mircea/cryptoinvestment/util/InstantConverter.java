package com.botez.mircea.cryptoinvestment.util;

import com.opencsv.bean.AbstractBeanField;

import java.time.Instant;

public class InstantConverter extends AbstractBeanField {

    @Override
    protected Object convert(String input) {
        return Instant.parse(input);
    }
}
