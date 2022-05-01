package com.snapstack.countriesapi.countries;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIncludeProperties({"cca3", "borders"})
public class Country {

    private String id;
    private List<String> borders;

    @JsonCreator
    public Country(@JsonProperty("cca3") String id,
                   @JsonProperty("borders") List<String> borders) {
        this.id = id;
        this.borders = borders;
    }

    public String getId() {
        return id;
    }

    public List<String> getBorders() {
        return borders;
    }
}
