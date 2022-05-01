package com.snapstack.countriesapi.countries;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CountriesCacheTest {

    private static final String COUNTRY1_ID = "CO1";
    private static final String COUNTRY2_ID = "CO2";
    private static final String COUNTRY3_ID = "CO3";

    @Mock
    private CountryLoader countryLoader;
    @Mock
    private Country country1;
    @Mock
    private Country country2;
    @Mock
    private Country country3;

    private CountriesCache countriesCache;

    @BeforeEach
    public void setup() throws IOException {
        when(countryLoader.loadCountries()).thenReturn(asList(country1, country2));

        when(country1.getId()).thenReturn(COUNTRY1_ID);
        when(country1.getBorders()).thenReturn(asList(COUNTRY2_ID, COUNTRY3_ID));
        when(country2.getId()).thenReturn(COUNTRY2_ID);
        when(country2.getBorders()).thenReturn(singletonList(COUNTRY1_ID));
        when(country3.getId()).thenReturn(COUNTRY3_ID);
        when(country3.getBorders()).thenReturn(singletonList(COUNTRY1_ID));
    }

    @Test
    public void should_create_graph_cache() throws IOException {
        countriesCache = new CountriesCache(countryLoader);

        verify(countryLoader).loadCountries();

        Graph<String, DefaultEdge> countriesGraph = countriesCache.getCountriesGraph();
        assertNotNull(countriesGraph);
        assertTrue(countriesGraph.containsVertex(COUNTRY1_ID));
        assertTrue(countriesGraph.containsVertex(COUNTRY2_ID));
        assertTrue(countriesGraph.containsVertex(COUNTRY3_ID));
        assertTrue(countriesGraph.containsEdge(COUNTRY1_ID, COUNTRY2_ID));
        assertTrue(countriesGraph.containsEdge(COUNTRY1_ID, COUNTRY3_ID));
        assertFalse(countriesGraph.containsEdge(COUNTRY2_ID, COUNTRY3_ID));
    }
}
