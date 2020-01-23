package com.skybooking.skyflightservice.v1_0_0.service.model.booking;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import lombok.Data;

@Data
public class BookingRequestTA {

    private final String bookingType = "flight";
    private BCreateRQ request;
    private String itineraryCode;

}
