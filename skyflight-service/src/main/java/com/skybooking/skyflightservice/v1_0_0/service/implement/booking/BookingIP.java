package com.skybooking.skyflightservice.v1_0_0.service.implement.booking;

import com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking.BookingSV;
import com.skybooking.skyflightservice.v1_0_0.transformer.observer.BookingOS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;
import org.springframework.stereotype.Service;

@Service
public class BookingIP implements BookingSV {

    @Override
    public BookingOS create(BCreateRQ BCreateRQ) {
        return new BookingOS();
    }

}
