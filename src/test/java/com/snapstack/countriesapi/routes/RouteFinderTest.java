package com.snapstack.countriesapi.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.snapstack.countriesapi.countries.CountriesCache;
import com.snapstack.countriesapi.countries.CountryValidator;
import com.snapstack.countriesapi.routes.exception.RouteNotFoundException;

@SpringBootTest
public class RouteFinderTest {

    private static final String VALID_ORIGIN = "ABC";
    private static final String VALID_DESTINATION = "XYZ";
    private static final String INVALID_DESTINATION = "ISO";

    @Mock
    private CountriesCache countriesCache;
    @Mock
    private CountryValidator countryValidator;
    @Spy
    private Graph<String, DefaultEdge> mockedGraph;

    private RouteFinder routeFinder;

    @BeforeEach
    public void setup() {
        routeFinder = new RouteFinder(countriesCache, countryValidator);

        mockedGraph = createMockedGraph();
        when(countriesCache.getCountriesGraph()).thenReturn(mockedGraph);
    }

    @Test
    public void should_find_route() {
        Route route = routeFinder.get(VALID_ORIGIN, VALID_DESTINATION);

        verify(countryValidator).validate(VALID_ORIGIN);
        verify(countryValidator).validate(VALID_DESTINATION);

        assertNotNull(route);
        List<String> routeList = route.getRoute();
        assertNotNull(routeList);
        assertEquals(2, routeList.size());
        assertTrue(routeList.contains(VALID_ORIGIN));
        assertTrue(routeList.contains(VALID_DESTINATION));
    }

    @Test
    public void should_not_find_route() {
        assertThrows(RouteNotFoundException.class, () -> routeFinder.get(VALID_ORIGIN, INVALID_DESTINATION));

        verify(countryValidator).validate(VALID_ORIGIN);
        verify(countryValidator).validate(INVALID_DESTINATION);
    }

    private Graph<String, DefaultEdge> createMockedGraph() {
        Graph<String, DefaultEdge> mockedGraph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
        mockedGraph.addVertex(VALID_ORIGIN);
        mockedGraph.addVertex(VALID_DESTINATION);
        mockedGraph.addVertex(INVALID_DESTINATION);
        mockedGraph.addEdge(VALID_ORIGIN, VALID_DESTINATION);
        return mockedGraph;
    }
}
