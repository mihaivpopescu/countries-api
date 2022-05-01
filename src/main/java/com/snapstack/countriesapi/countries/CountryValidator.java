package com.snapstack.countriesapi.countries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snapstack.countriesapi.countries.exception.CountryNotFoundException;

public class CountryValidator {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final CountriesCache countriesCache;

    public CountryValidator(CountriesCache countriesCache) {
        this.countriesCache = countriesCache;
    }

    public void validate(String countryId) {
        if (!countriesCache.getCountriesGraph().containsVertex(countryId)) {
            LOGGER.error("Invalid country : {} !", countryId);
            throw new CountryNotFoundException();
        }
    }
}
