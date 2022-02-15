package com.info.country.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.info.country.service.dto.country.Name;
import com.info.country.service.dto.currency.CurrencyExchangeRate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "countries")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private Name name;
    private String population;
    private List<String> tld;
    private String cca2;
    private String ccn3;
    private String cca3;
    private String cioc;
    private String independent;
    private String status;
    private String unMember;
    private Object currencies;
    private List<CurrencyExchangeRate> currencyExchangeRates;
    private List<String> capital;
    private List<String> altSpellings;
    private String region;
    private String subregion;
    private Date createdTime;
}
