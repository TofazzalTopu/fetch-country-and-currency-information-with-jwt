package com.info.country.service.impl;

import com.info.country.constant.AppConstants;
import com.info.country.exceptions.NotFoundException;
import com.info.country.model.Country;
import com.info.country.repository.CountryRepository;
import com.info.country.service.CountryService;
import com.info.country.service.dto.currency.CurrencyExchangeRate;
import com.info.country.service.dto.currency.CurrencyExchangeRateResponse;
import com.info.country.service.dto.currency.CurrencyInfo;
import com.info.country.utility.TimeUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Md. Tofazzal Hossain
 * Date : 15-02-2022
 */

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    @Value("${country_api_url}")
    String COUNTRY_API_URL;

    @Value("${exchange_rate_api_access_key}")
    String EXCHANGE_RATE_API_KEY;

    @Value("${latest_exchange_rate_url}")
    String CURRENCY_LATEST_EXCHANGE_RATE_URL;

    private final CountryRepository countryRepository;

    private final ModelMapper modelMapper;

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country update(String countryId, Country country) throws NotFoundException {
        Country savedCountry = findById(countryId);
        return countryRepository.save(savedCountry);
    }

    @Override
    public Country findById(String countryId) throws NotFoundException {
        return countryRepository.findById(countryId).orElseThrow(() -> new NotFoundException(AppConstants.COUNTRY_NOT_FOUND_WITH_THE_GIVEN_ID + countryId));
    }

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll(Sort.by("id").descending());
    }

    @Override
    public List<Country> findCountryByName(String name) {
        limitNumberOfRequestPerMinute();
        List<Country> countryList = Arrays.asList(getCountries(name)).stream().map(this::getCountry).collect(Collectors.toList());
        return countryRepository.saveAll(countryList);
    }

    private Country[] getCountries(String name) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Country[]> countryResponse = restTemplate
                .exchange(COUNTRY_API_URL + name, HttpMethod.GET, getHttpEntity(), Country[].class);
        log.info("Country fetched successfully.");
        return countryResponse.getBody();
    }

    private Country getCountry(Country country) {
        LinkedHashMap<String, Object> linkedHashMap = (LinkedHashMap<String, Object>) country.getCurrencies();
        Set<String> keys = linkedHashMap.keySet();
        List<CurrencyExchangeRate> currencyExchangeRateList = new ArrayList<>();
        keys.stream().forEach(key -> currencyExchangeRateList.addAll(getCurrencyExchangeRateList(linkedHashMap, key)));
        country.setCurrencyExchangeRates(currencyExchangeRateList);
        country.setCreatedTime(new Date());
        return country;
    }

    private List<CurrencyExchangeRate> getCurrencyExchangeRateList(LinkedHashMap<String, Object> linkedHashMap, String key) {
        List<CurrencyExchangeRate> currencyExchangeRateList = new ArrayList<>();
        CurrencyExchangeRateResponse currencyResponse = getCurrencyExchangeRate(key);
        CurrencyExchangeRate currencyExchangeRate = modelMapper.map(currencyResponse, CurrencyExchangeRate.class);
        currencyExchangeRate.setName(key);
        currencyExchangeRate.setInfo(getCurrencyInfo(linkedHashMap, key));
        currencyExchangeRateList.add(currencyExchangeRate);
        return currencyExchangeRateList;
    }

    private CurrencyExchangeRateResponse getCurrencyExchangeRate(String baseCurrency) {
        RestTemplate restTemplate = new RestTemplate();
        CURRENCY_LATEST_EXCHANGE_RATE_URL = CURRENCY_LATEST_EXCHANGE_RATE_URL + "&symbols=INR";
        ResponseEntity<CurrencyExchangeRateResponse> exchangeRates = restTemplate
                .exchange(CURRENCY_LATEST_EXCHANGE_RATE_URL, HttpMethod.GET, getHttpEntity(), CurrencyExchangeRateResponse.class);
        return exchangeRates.getBody();
    }

    private CurrencyInfo getCurrencyInfo(LinkedHashMap<String, Object> linkedHashMap, String key) {
        LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) linkedHashMap.get(key);
        CurrencyInfo currencyInfo = new CurrencyInfo();
        currencyInfo.setName(map.get("name"));
        currencyInfo.setSymbol(map.get("symbol"));
        return currencyInfo;
    }

    private HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("X-COM-PERSIST", "NO");
        headers.set("X-COM-LOCATION", "USA");
        return new HttpEntity<>(headers);
    }

    private void limitNumberOfRequestPerMinute() {
        List<Country> countryList = countryRepository.findAllByCreatedTimeGreaterThan(TimeUtility.getPreviousTimeInMinutes(1));
        if (countryList.size() == 30)
            throw new NotFoundException(AppConstants.YOU_COULD_NOT_REQUEST_COUNTRY_API_MORE_THAN_THIRTY_TIMES_WITHIN_A_MINUTE);
    }
}
