package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingAirItinPriceTQ {

    private String passType;
    private Integer passQty;
    private BigDecimal baseFare;
    private BigDecimal totalTax;
    private BigDecimal totalAmount;
    private String currencyCode;
    private boolean nonRefund;

}
