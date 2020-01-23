package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SegmentRS implements Serializable {

    private String id;
    private String departure = "";
    private String departureTime = "";
    private String departureTerminal = "";
    private String arrival = "";
    private String arrivalTime = "";
    private String arrivalTerminal = "";
    private String flightNumber;
    private String airline = "";
    private String aircraft = "";
    private String operatingAirline = "";
    private String operatingFlightNumber = "";
    private int seatsRemain;
    private String cabin = "";
    private String cabinName = "";
    private String meal = "";
    private String mealName = "";
    private String bookingCode = "";
    private int stopCount;
    private List<HiddenStopRS> hiddenStops = new ArrayList<>();

}
