package com.snapstack.countriesapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.snapstack.countriesapi.routes.RoutingController;

@SpringBootTest
class CountriesApiApplicationTests {

    @Autowired
    private RoutingController routingController;

    @Test
    void contextLoads() {
        assertNotNull(routingController);
    }
}
