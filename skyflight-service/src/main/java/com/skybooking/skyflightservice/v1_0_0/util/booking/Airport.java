package com.skybooking.skyflightservice.v1_0_0.util.booking;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class Airport {

    @CsvBindByPosition(position = 0)
    private Long airportId;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private String city;

    @CsvBindByPosition(position = 3)
    private String country;

    @CsvBindByPosition(position = 4)
    private String iata;

    @CsvBindByPosition(position = 5)
    private String icao;

    @CsvBindByPosition(position = 6)
    private String latitude;

    @CsvBindByPosition(position = 7)
    private String longitude;

    @CsvBindByPosition(position = 8)
    private String altitude;

    @CsvBindByPosition(position = 9)
    private String timezone;

    @CsvBindByPosition(position = 10)
    private String dst;

    @CsvBindByPosition(position = 11)
    private String zone;

    @CsvBindByPosition(position = 12)
    private String type;

    @CsvBindByPosition(position = 13)
    private String source;

}
