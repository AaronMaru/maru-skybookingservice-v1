package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ItineraryODSegmentRS {

    private String airlineCode;
    private String airlineName;
    private String airlineLogo;
    private Integer flightNumber;
    private String equipType;
    private String aircraftName;
    private String meal;
    private String mealName;
    private Integer stopQty;
    private Integer layover;
    private String airlineRef;
    private String operatedByAirline;
    private Integer adjustmentDate;

    private String departureLocationCode;
    private String departureLocationName;
    private String departureTerminal;
    private Date departureDate;

    private String arrivalLocationCode;
    private String arrivalLocationName;
    private String arrivalTerminal;
    private Date arrivalDate;
    List<BookingStopInfoRS> stopInfo = new ArrayList<>();
}
