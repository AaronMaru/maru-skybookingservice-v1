package com.skybooking.skyhistoryservice.v1_0_0.transformer.mail;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryODSegmentTF {

    private Integer elapsedTime;
    private String airlineCode;
    private String airlineName;
    private String airlineLogo;
    private String flightNumber;
    private String equipType;
    private String aircraftName;
    private String meal;
    private String mealName;
    private Integer stopQty;
    private Integer layover;
    private String layoverHourMinute;
    private String airlineRef;
    private String operatedByAirline;
    private Integer adjustmentDate;
    private String status;

    DepartureTF departureInfo;
    ArrivalTF arrivalInfo;

    List<BookingStopInfoTF> stopInfo = new ArrayList<>();
}
