package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import lombok.Data;

import java.io.Serializable;

@Data
public class TotalFare implements Serializable {
    private String currencyCode;
    private double content;
}
