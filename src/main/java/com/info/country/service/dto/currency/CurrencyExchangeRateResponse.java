package com.info.country.service.dto.currency;

import lombok.Data;

@Data
public class CurrencyExchangeRateResponse {
    private boolean success;
    private Integer timestamp;
    private String base;
    private String date;
    private Object rates;
}
