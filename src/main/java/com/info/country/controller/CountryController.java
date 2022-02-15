package com.info.country.controller;

import com.info.country.model.Country;
import com.info.country.service.CountryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/countries")
@ApiOperation(value = "Country information with currency exchange rates")
public class CountryController {

    private final CountryService countryService;

    @ApiOperation(value = "Fetch Country By Name")
    @GetMapping(value = "/{name}")
    public List<Country> findCountryByName(@PathVariable String name) {
         return countryService.findCountryByName(name);
    }

}
