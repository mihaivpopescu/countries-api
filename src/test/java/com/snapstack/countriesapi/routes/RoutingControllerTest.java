package com.snapstack.countriesapi.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.snapstack.countriesapi.countries.exception.CountryNotFoundException;
import com.snapstack.countriesapi.routes.exception.RouteNotFoundException;

@SpringBootTest
public class RoutingControllerTest {

    private static final String ORIGIN = "ABC";
    private static final String DESTINATION = "XYZ";

    @Mock
    private RouteFinder routeFinder;
    @Mock
    private Route route;

    private RoutingController routingController;

    @BeforeEach
    public void setup() {
        routingController = new RoutingController(routeFinder);
    }

    @Test
    public void should_return_ok_when_route_found() {
        when(routeFinder.get(ORIGIN, DESTINATION)).thenReturn(route);

        ResponseEntity<Route> response = routingController.getRouting(ORIGIN, DESTINATION);

        verify(routeFinder).get(ORIGIN, DESTINATION);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(route, response.getBody());
    }

    @Test
    public void should_return_not_found_if_invalid_country() {
        when(routeFinder.get(ORIGIN, DESTINATION)).thenThrow(CountryNotFoundException.class);

        ResponseEntity<Route> response = routingController.getRouting(ORIGIN, DESTINATION);

        verify(routeFinder).get(ORIGIN, DESTINATION);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void should_return_bad_request_if_route_not_existing() {
        when(routeFinder.get(ORIGIN, DESTINATION)).thenThrow(RouteNotFoundException.class);

        ResponseEntity<Route> response = routingController.getRouting(ORIGIN, DESTINATION);

        verify(routeFinder).get(ORIGIN, DESTINATION);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
