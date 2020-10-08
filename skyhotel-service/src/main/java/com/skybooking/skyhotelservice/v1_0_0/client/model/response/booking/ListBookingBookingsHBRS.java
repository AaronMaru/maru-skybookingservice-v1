package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import lombok.Data;

import java.util.List;

@Data
public class ListBookingBookingsHBRS {
    private List<BookingHBRS> bookings;
    private Integer from;
    private Integer to;
    private Integer total;
}
