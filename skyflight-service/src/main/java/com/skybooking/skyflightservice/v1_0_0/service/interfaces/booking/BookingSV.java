package com.skybooking.skyflightservice.v1_0_0.service.interfaces.booking;

import com.skybooking.skyflightservice.v1_0_0.transformer.observer.BookingOS;
import com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking.BCreateRQ;

public interface BookingSV {

    BookingOS create(BCreateRQ BCreateRQ);
}
