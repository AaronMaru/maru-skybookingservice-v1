package com.skybooking.skyflightservice.v1_0_0.service.model.booking;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TravelItineraryTA {

    private BigDecimal amount;
    private String passengerType;
    private String currencyCode;

}
