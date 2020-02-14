package com.skybooking.paymentservice.v1_0_0.client.flight.ui.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FlightMandatoryDataRS {

    private BigDecimal amount;
    private String Description;
    private Integer paymentId;
    private String name;
    private String email;
    private String phoneNumber;

}