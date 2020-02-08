package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Segment implements Serializable {

    @Id
    private String id;
    private String departure;
    private String departureTime;
    private String departureTerminal;
    private String arrival;
    private String arrivalTime;
    private String arrivalTerminal;
    private String flightNumber;
    private String airline;
    private String aircraft;
    private String operatingAirline;
    private String operatingFlightNumber;
    private int stopCount;
    private int segmentIndex;
    private int directionIndex;
    private List<HiddenStop> hiddenStops = new ArrayList<>();


}
