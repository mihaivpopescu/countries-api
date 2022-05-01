package com.snapstack.countriesapi.routes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.snapstack.countriesapi.countries.CountriesCache;
import com.snapstack.countriesapi.countries.CountryValidator;
import com.snapstack.countriesapi.routes.RouteFinder;

@Configuration
public class RouteConfig {

    @Autowired
    private CountriesCache countriesCache;
    @Autowired
    private CountryValidator countryValidator;

    @Bean
    public RouteFinder routeFinder() {
        return new RouteFinder(countriesCache, countryValidator);
    }
}
