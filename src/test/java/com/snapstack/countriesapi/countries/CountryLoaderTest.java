package com.snapstack.countriesapi.countries;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CountryLoaderTest {

    private static final String VALID_COUNTRIES_URL = "https://raw.githubusercontent.com/mledoze/countries/master/countries.json";
    private static final String INVALID_COUNTRIES_URL = "https://mockurlusedonlyhere.1234567890.org";

    private CountryLoader countryLoader;

    @Test
    public void should_load_countries() {
        countryLoader = new CountryLoader(VALID_COUNTRIES_URL);

        try {
            List<Country> countries = countryLoader.loadCountries();

            assertNotNull(countries);
        } catch (IOException e) {
            fail("Failed to retrieve countries from external url.");
        }
    }

    @Test
    public void should_throw_exception_if_connection_failed() {
        countryLoader = new CountryLoader(INVALID_COUNTRIES_URL);

        assertThrows(IOException.class, () -> countryLoader.loadCountries());
    }
}
