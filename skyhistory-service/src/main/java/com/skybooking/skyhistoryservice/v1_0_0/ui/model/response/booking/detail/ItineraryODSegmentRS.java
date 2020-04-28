package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.destination.ArrivalRS;
import com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail.destination.DepartureRS;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryODSegmentRS {

    private Integer elapsedTime;
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
    private String status;

    DepartureRS departureInfo;
    ArrivalRS arrivalInfo;

    List<BookingStopInfoRS> stopInfo = new ArrayList<>();
}
