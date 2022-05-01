package com.snapstack.countriesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.snapstack.countriesapi.routes.RouteFinder;
import com.snapstack.countriesapi.routes.RoutingController;

@SpringBootApplication
public class CountriesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountriesApiApplication.class, args);
    }
}
