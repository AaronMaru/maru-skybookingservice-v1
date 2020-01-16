package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

@Data
public class SegmentRS {

    private String id;
    private int duration;
    private String departure;
    private String departureTime;
    private String departureTerminal;
    private String arrival;
    private String arrivalTime;
    private String arrivalTerminal;
    private String flightNumber;
    private String operationAirline;
    private String airline;
    private String aircraft;
    private int seatsRemain;
    private String cabin;
    private String cabinName;
    private String meal;
    private String mealName;

}
