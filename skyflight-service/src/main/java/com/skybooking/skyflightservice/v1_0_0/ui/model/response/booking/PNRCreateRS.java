package com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

@Data
public class PNRCreateRS {

    private String bookingCode = "";
    private String bookingRef = "";

    public PNRCreateRS(String bookingCode, String bookingRef) {
        this.bookingCode = bookingCode;
        this.bookingRef = bookingRef;
    }

    public PNRCreateRS() {
    }
}
