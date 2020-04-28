package com.skybooking.skyflightservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

@Data
public class PNRCreateRS {

    private String bookingCode = "";


    public PNRCreateRS(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public PNRCreateRS() {
    }
}
