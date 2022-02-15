package com.info.country.service.dto.currency;

import lombok.Data;

@Data
public class CurrencyExchangeRate {

    private String name;
    private CurrencyInfo info;
    private String base;
    private String date;
    private Object rates;
}
