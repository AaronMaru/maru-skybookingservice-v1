package com.skybooking.skyhistoryservice.v1_0_0.transformer.mail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookingStopInfoTF {

    private Integer elapsedTime;
    private String elapsedHourMinute;
    private String locationCode;
    private String airportName;
    private String city;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Date departureDate;
    private Date arrivalDate;
    private Integer duration;

}
