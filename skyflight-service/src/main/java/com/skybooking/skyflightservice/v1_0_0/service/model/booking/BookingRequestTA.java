package com.skybooking.skyflightservice.v1_0_0.service.model.booking;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingCreateRQ;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookingRequestTA {

    private final String bookingType = "flight";
    private BookingCreateRQ request;
    private String itineraryCode;
    private List<String> reservationsCode = new ArrayList<>();

}
