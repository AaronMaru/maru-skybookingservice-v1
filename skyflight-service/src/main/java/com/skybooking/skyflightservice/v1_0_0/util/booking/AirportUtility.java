package com.skybooking.skyflightservice.v1_0_0.util.booking;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class AirportUtility {

    private final HashMap<String, Airport> airports = new HashMap<>();

    @SneakyThrows
    @PostConstruct
    private void init() {

        String AIRPORT_CSV_FILE_PATH = "csv/airports.dat";

        var resourceFile = new ClassPathResource(AIRPORT_CSV_FILE_PATH);
        var reader = new InputStreamReader(resourceFile.getInputStream(), UTF_8);
        var csvToBean = new CsvToBeanBuilder<Airport>(reader).withType(Airport.class).withIgnoreLeadingWhiteSpace(true).build();

        csvToBean
                .iterator()
                .forEachRemaining(airport -> {
                    airports.putIfAbsent(airport.getIata(), airport);
                });
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * get airport information by IATA code
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param iata
     * @return
     */
    public Airport findOne(String iata) {
        return airports.getOrDefault(iata, null);
    }

}
