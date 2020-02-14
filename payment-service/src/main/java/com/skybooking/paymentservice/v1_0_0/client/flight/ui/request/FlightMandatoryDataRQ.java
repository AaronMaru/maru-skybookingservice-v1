package com.skybooking.paymentservice.v1_0_0.client.flight.ui.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FlightMandatoryDataRQ {

    private String bookingCode;
    private String paymentCode;
    private BigDecimal percentage;
    private BigDecimal percentageBase;

    public FlightMandatoryDataRQ(String bookingCode, String paymentCode, BigDecimal percentage, BigDecimal percentageBase) {
        this.bookingCode = bookingCode;
        this.paymentCode = paymentCode;
        this.percentage = percentage;
        this.percentageBase = percentageBase;
    }
}
