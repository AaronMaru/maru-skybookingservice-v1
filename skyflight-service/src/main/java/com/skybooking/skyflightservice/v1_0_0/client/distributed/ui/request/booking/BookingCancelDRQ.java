package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.booking;

import lombok.Data;

@Data
public class BookingCancelDRQ {

    private String itineraryReference;

    public BookingCancelDRQ(String itineraryReference) {
        this.itineraryReference = itineraryReference;
    }
}
