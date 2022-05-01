package com.snapstack.countriesapi.routes;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snapstack.countriesapi.countries.CountriesCache;
import com.snapstack.countriesapi.countries.CountryValidator;
import com.snapstack.countriesapi.routes.exception.RouteNotFoundException;

public class RouteFinder {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final CountriesCache countriesCache;
    private final CountryValidator countryValidator;

    public RouteFinder(CountriesCache countriesCache, CountryValidator countryValidator) {
        this.countriesCache = countriesCache;
        this.countryValidator = countryValidator;
    }

    public Route get(String origin, String destination) {
        countryValidator.validate(origin);
        countryValidator.validate(destination);

        GraphPath<String, DefaultEdge> shortestPath = getShortestPath(origin, destination);

        return new Route(shortestPath.getVertexList());
    }

    private GraphPath<String, DefaultEdge> getShortestPath(String origin, String destination) {
        DijkstraShortestPath<String, DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<String, DefaultEdge>(countriesCache.getCountriesGraph());
        GraphPath<String, DefaultEdge> shortestPath = dijkstraShortestPath.getPath(origin, destination);
        if (shortestPath == null) {
            LOGGER.info("No route found between {} and {} !", origin, destination);
            throw new RouteNotFoundException();
        }
        return shortestPath;
    }
}
