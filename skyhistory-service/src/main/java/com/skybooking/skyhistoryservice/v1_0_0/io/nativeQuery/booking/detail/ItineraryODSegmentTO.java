package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ItineraryODSegmentTO {

    private Integer id;
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

    private String departureLocationCode;
    private String departureAirportName;
    private String departureCity;
    private String departureCountry;
    private BigDecimal departureLatitude;
    private BigDecimal departureLongitude;
    private String departureTerminal;
    private Date departureDate;

    private String arrivalLocationCode;
    private String arrivalAirportName;
    private BigDecimal arrivalLatitude;
    private BigDecimal arrivalLongitude;
    private String arrivalCity;
    private String arrivalCountry;
    private String arrivalTerminal;
    private Date arrivalDate;
    private String status;

}
