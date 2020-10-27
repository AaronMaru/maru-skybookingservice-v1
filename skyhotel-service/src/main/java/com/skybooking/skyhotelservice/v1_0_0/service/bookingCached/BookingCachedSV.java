package com.skybooking.skyhotelservice.v1_0_0.service.bookingCached;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.cached.CheckRateCached;
import org.springframework.stereotype.Service;

@Service
public interface BookingCachedSV {
    void save(String bookingId, CheckRateCached checkRateCached);
    CheckRateCached retrieve(String bookingId);
}
