package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookingCancelRQ {

    @NotNull(message = "Booking Id is required")
    private Integer bookingId;

}
