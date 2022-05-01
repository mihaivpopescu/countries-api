package com.snapstack.countriesapi.routes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.snapstack.countriesapi.countries.exception.CountryNotFoundException;
import com.snapstack.countriesapi.routes.exception.RouteNotFoundException;

@RestController
public class RoutingController {

    private final RouteFinder routeFinder;

    public RoutingController(RouteFinder routeFinder) {
        this.routeFinder = routeFinder;
    }

    @GetMapping("/routing/{origin}/{destination}")
    public ResponseEntity<Route> getRouting(@PathVariable String origin, @PathVariable String destination) {
        try {
            Route route = routeFinder.get(origin, destination);
            return ResponseEntity.status(HttpStatus.OK).body(route);
        } catch (CountryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RouteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
