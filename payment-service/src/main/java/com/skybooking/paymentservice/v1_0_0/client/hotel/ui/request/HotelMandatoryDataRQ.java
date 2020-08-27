package com.skybooking.paymentservice.v1_0_0.client.hotel.ui.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelMandatoryDataRQ {

    private String bookingCode;
    private String paymentCode;
    private BigDecimal percentage;
    private BigDecimal percentageBase;

    public HotelMandatoryDataRQ(String bookingCode, String paymentCode, BigDecimal percentage, BigDecimal percentageBase) {
        this.bookingCode = bookingCode;
        this.paymentCode = paymentCode;
        this.percentage = percentage;
        this.percentageBase = percentageBase;
    }

}
