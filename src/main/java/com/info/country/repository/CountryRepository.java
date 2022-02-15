package com.info.country.repository;

import com.info.country.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface CountryRepository extends MongoRepository<Country, String> {
    List<Country> findAllByCreatedTimeGreaterThan(Date preValidationRequestedTime);

    List<Country> findCountryByName(String name);

}
