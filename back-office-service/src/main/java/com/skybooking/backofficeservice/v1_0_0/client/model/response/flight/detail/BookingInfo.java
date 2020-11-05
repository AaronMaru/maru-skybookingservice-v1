package com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.detail;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingInfo {
    private BigDecimal bookingId;
    private String bookingCode;
    private String transactionCode;
    private String pnrCode;
    private String tripType;
    private Integer adult;
    private int child;
    private int infant;
    private Integer totalPass;
    private String cabinName;
    private String urlItinerary;
    private String urlReceipt;
    private String bookingByUsername;
    private String bookingByUserPhoto;
    private String bookingDate;
    private String statusKey;
    private String issuedDate;
}
