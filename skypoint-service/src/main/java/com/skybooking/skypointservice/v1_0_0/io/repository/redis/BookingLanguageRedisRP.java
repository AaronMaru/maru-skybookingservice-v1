package com.skybooking.skypointservice.v1_0_0.io.repository.redis;

import com.skybooking.skypointservice.v1_0_0.io.entity.redis.BookingLanguageCached;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by : maru
 * Date  : 4/30/2020
 * Time  : 2:35 PM
 */

@Repository
public interface BookingLanguageRedisRP extends CrudRepository<BookingLanguageCached, String> {
    BookingLanguageCached findByBookingCode(String bookingCode);
}
