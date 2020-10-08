package com.skybooking.skyhotelservice.v1_0_0.service.bookingCached;

import com.hazelcast.core.HazelcastInstance;
import com.skybooking.skyhotelservice.config.AppConfig;
import com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.cached.CheckRateCached;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BookingCachedIP implements BookingCachedSV {

    private static final String HOTEL_BOOKING_CACHED = "HOTEL_BOOKING_CACHED";

    @Qualifier("hazelcastInstance")
    private final HazelcastInstance hazelcastInstance;
    private final AppConfig appConfig;

    @Override
    public void save(String bookingId, CheckRateCached checkRateCached) {
        hazelcastInstance
            .getMap(HOTEL_BOOKING_CACHED)
            .put(bookingId,
                checkRateCached,
                appConfig.getHAZELCAST_EXPIRED_TIME(),
                TimeUnit.SECONDS);
    }

    @Override
    public CheckRateCached retrieve(String bookingId) {
        return (CheckRateCached) hazelcastInstance
            .getMap(HOTEL_BOOKING_CACHED)
            .getOrDefault(bookingId, null);
    }

}
