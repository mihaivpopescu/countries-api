package com.snapstack.countriesapi.countries;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.snapstack.countriesapi.countries.exception.CountryNotFoundException;

@SpringBootTest
public class CountryValidatorTest {

    private static final String VALID_COUNTRY = "ABC";
    private static final String INVALID_COUNTRY = "XYZ";

    @Mock
    private CountriesCache countriesCache;
    @Mock
    private Graph<String, DefaultEdge> graph;

    private CountryValidator countryValidator;

    @BeforeEach
    public void setup() {
        countryValidator = new CountryValidator(countriesCache);

        when(countriesCache.getCountriesGraph()).thenReturn(graph);
        when(graph.containsVertex(VALID_COUNTRY)).thenReturn(true);
        when(graph.containsVertex(INVALID_COUNTRY)).thenReturn(false);
    }

    @Test
    public void should_validate_country() {
        countryValidator.validate(VALID_COUNTRY);

        verify(graph).containsVertex(VALID_COUNTRY);
    }

    @Test
    public void should_not_validate_country() {
        assertThrows(CountryNotFoundException.class, () -> countryValidator.validate(INVALID_COUNTRY));

        verify(graph).containsVertex(INVALID_COUNTRY);
    }
}
