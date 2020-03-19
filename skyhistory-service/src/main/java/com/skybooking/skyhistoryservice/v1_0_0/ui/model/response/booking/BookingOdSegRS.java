package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

@Data
public class BookingOdSegRS {

    private String airlineCode;
    private String airlineName;
    private Integer flightNumber;
    private String equipType;
    private String airCraftName;
    private String airlineLogo45;
    private String airlineLogo90;
    private String status;
    private Integer adjustmentDate;

    private String depDateTime;
    private String depTerminal;
    private String depLocation;
    private String depLocationName;
    private String depCity;
    private String depCountry;

    private String arrDateTime;
    private String arrTerminal;
    private String arrLocation;
    private String arrLocationName;
    private String arrCity;
    private String arrCountry;

}
