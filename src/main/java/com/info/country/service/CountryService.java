package com.info.country.service;

import com.info.country.exceptions.NotFoundException;
import com.info.country.model.Country;

import java.util.List;

public interface CountryService {

    Country save(Country country);
    Country update(String countryId, Country country) throws NotFoundException;
    Country findById(String countryId) throws NotFoundException;
    List<Country> findAll();
    void delete(String countryId);

    List<Country> findCountryByName(String name);
}
