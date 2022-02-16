package com.info.country.controller;

import com.info.country.constant.AppConstants;
import com.info.country.model.Country;
import com.info.country.service.CountryService;
import com.info.country.service.dto.responses.Response;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/" + AppConstants.API_VERSION + "/countries")
@ApiOperation(value = "Country information with currency exchange rates")
public class CountryController {

    private final CountryService countryService;

    @ApiOperation(value = "Fetch Country By Name")
    @GetMapping(value = "/{name}")
    public ResponseEntity<Response<List<Country>>> findCountryByName(@PathVariable String name) {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, countryService.findCountryByName(name)));
    }
    @ApiOperation(value = "Fetch All countries")
    @GetMapping
    public ResponseEntity<Response<List<Country>>> list() {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, countryService.findAll()));
    }

    @ApiOperation(value = "Fetch country by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<Country>> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(new Response<>(HttpStatus.OK.value(),
                AppConstants.USER_FETCH_SUCCESS, countryService.findById(id)));
    }

    @ApiOperation(value = "Delete country by id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response<Void>> delete(@PathVariable String id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
