package com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight.flightDetail;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingInfo {
    private BigDecimal bookingId;
    private String bookingCode;
    private String transactionCode;
    private String pnrCode;
    private String tripType;
    private String bookingDate;
    private String issuedDate;
}
