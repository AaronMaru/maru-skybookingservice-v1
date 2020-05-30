package com.skybooking.paymentservice.v1_0_0.io.repository.redis;

import com.skybooking.paymentservice.v1_0_0.io.enitity.redis.BookingLanguageCached;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by : maru
 * Date  : 4/30/2020
 * Time  : 2:35 PM
 */

@Repository
public interface BookingLanguageRedisRP extends CrudRepository<BookingLanguageCached, String> {
}
