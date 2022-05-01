package com.snapstack.countriesapi.countries.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.snapstack.countriesapi.countries.CountriesCache;
import com.snapstack.countriesapi.countries.CountryLoader;
import com.snapstack.countriesapi.countries.CountryValidator;

@Configuration
public class CountryConfig {

    @Value("${countries.url}")
    private String countriesURL;

    @Bean
    public CountryLoader countryLoader() {
        return new CountryLoader(countriesURL);
    }

    @Bean
    public CountriesCache countriesCache(CountryLoader countryLoader) throws IOException {
        return new CountriesCache(countryLoader);
    }

    @Bean
    public CountryValidator countryValidator(CountriesCache countriesCache) {
        return new CountryValidator(countriesCache);
    }
}
