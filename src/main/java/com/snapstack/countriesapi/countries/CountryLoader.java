package com.snapstack.countriesapi.countries;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CountryLoader {

    private final String countriesURL;

    public CountryLoader(String countriesURL) {
        this.countriesURL = countriesURL;
    }

    public List<Country> loadCountries() throws IOException {
        URL url = new URL(countriesURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("accept", "application/json");

        InputStream responseStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseStream, new TypeReference<List<Country>>() {
        });
    }
}
