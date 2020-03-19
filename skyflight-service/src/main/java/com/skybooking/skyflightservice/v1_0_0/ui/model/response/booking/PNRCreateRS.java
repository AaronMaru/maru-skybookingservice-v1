package com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

@Data
public class PNRCreateRS {

    private String bookingCode = "";
    private String bookingRef = "";
    private Integer bookingId;

    public PNRCreateRS(String bookingCode, String bookingRef, Integer bookingId) {
        this.bookingCode = bookingCode;
        this.bookingRef = bookingRef;
        this.bookingId = bookingId;
    }

    public PNRCreateRS() {
    }
}
