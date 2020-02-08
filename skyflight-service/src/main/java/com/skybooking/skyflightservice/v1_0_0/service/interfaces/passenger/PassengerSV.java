package com.skybooking.skyflightservice.v1_0_0.service.interfaces.passenger;

import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BookingPassengerRQ;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PassengerSV {
    void create(List<BookingPassengerRQ> passengers);
}
