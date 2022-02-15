package com.info.country.service.dto.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CurrencyInfo {
    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("symbol")
    @SerializedName("symbol")
    private String symbol;

}
