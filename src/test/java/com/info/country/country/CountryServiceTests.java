package com.info.country.country;

import com.info.country.model.Country;
import com.info.country.repository.CountryRepository;
import com.info.country.service.impl.CountryServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTests {

    @InjectMocks
    private CountryServiceImpl countryService;
    @Mock
    private CountryRepository countryRepository;

    @Test
    public void findById() {
        String id = "111111";
        Country country = new Country();
        country.setId(id);
        Mockito.when(countryRepository.findById(id)).thenReturn(Optional.of(country));
        Country findCountry = countryService.findById(id);
        Assert.assertEquals(findCountry, country);
    }
}
