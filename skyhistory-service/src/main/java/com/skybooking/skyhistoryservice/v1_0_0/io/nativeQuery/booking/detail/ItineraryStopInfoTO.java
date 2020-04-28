package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ItineraryStopInfoTO {

    private Integer elapsedTime;
    private String locationCode;
    private String airportName;
    private String city;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Date departureDate;
    private Date arrivalDate;
    private String duration;
    private String equipment;

}
