package com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.detail;

import lombok.Data;

import java.util.List;

@Data
public class ItinerarySegment {
    private String airlineCode;
    private String airlineRef;
    private String airlineName;
    private String aircraftName;
    private String flightNumber;
    private String mealName;
    private String status;
    private DepartureInfo departureInfo;
    private ArrivalInfo arrivalInfo;
    private List<StopInfo> stopInfo;
}
