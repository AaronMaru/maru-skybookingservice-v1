package com.skybooking.skyhotelservice.v1_0_0.service.booking;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.HotelBookingEntity;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking.CheckRateRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking.ReserveRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.StructureRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.CheckRateRS;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.payment.PaymentTransactionRQ;

public interface BookingSV {
    StructureRS checkRate(CheckRateRQ checkRate);
    StructureRS reserve(ReserveRQ reserveRQ);
    void storeHotelBooking(CheckRateRS checkRateRS);
    void confirmBooking(HotelBookingEntity bookingEntity, PaymentTransactionRQ paymentTransactionRQ);
}
