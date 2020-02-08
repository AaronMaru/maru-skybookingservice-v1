package com.skybooking.skyflightservice.v1_0_0.service.model.booking;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import lombok.Data;

@Data
public class BookingRequestTA {

    private final String bookingType = "flight";
    private BookingCreateRQ request;
    private String itineraryCode;

}
