package com.skybooking.skyflightservice.v1_0_0.service.interfaces.baggages;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.BookingRequestTA;
import org.springframework.stereotype.Service;

@Service
public interface BaggageSV {
    Boolean insertBaggage(BookingRequestTA requestTA, Integer bookingId);
}
