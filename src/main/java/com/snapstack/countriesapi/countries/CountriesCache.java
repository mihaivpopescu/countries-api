package com.snapstack.countriesapi.countries;

import java.io.IOException;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountriesCache {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final CountryLoader countryLoader;

    private Graph<String, DefaultEdge> countriesGraph;

    public CountriesCache(CountryLoader countryLoader) throws IOException {
        this.countryLoader = countryLoader;
        createCache();
    }

    private void createCache() throws IOException {
        LOGGER.info("Loading countries in cache. This may take a few seconds...");
        try {
            createGraph(countryLoader.loadCountries());
        } catch (IOException e) {
            LOGGER.error("Failed to load countries in cache!", e);
            throw e;
        }
        LOGGER.info("Successfully loaded countries in cache.");
    }

    private void createGraph(List<Country> countries) {
        countriesGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
        countries.forEach(country -> {
            countriesGraph.addVertex(country.getId());
            country.getBorders().forEach(border -> {
                countriesGraph.addVertex(border);
                countriesGraph.addEdge(country.getId(), border);
            });
        });
    }

    public Graph<String, DefaultEdge> getCountriesGraph() {
        return countriesGraph;
    }
}
