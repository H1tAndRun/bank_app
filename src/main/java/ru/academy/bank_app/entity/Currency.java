package ru.academy.bank_app.entity;

import lombok.Getter;
import java.math.BigDecimal;

@Getter
public enum Currency {

    RUB("810", new BigDecimal("1")),
    USD("840", new BigDecimal("30")),
    EU("978", new BigDecimal("35"));

    private final String currencyCode;

    private final BigDecimal nominal;

    Currency(String currencyCode, BigDecimal nominal) {
        this.currencyCode = currencyCode;
        this.nominal = nominal;
    }
}
