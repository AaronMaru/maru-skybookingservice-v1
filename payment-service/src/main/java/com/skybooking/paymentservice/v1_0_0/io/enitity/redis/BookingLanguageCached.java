package com.skybooking.paymentservice.v1_0_0.io.enitity.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Created by : maru
 * Date  : 1/15/2020
 * Time  : 3:08 PM
 */
@RedisHash(value = "booking-language", timeToLive = 60 * 60)
public class BookingLanguageCached {

    @Id
    private String bookingCode;

    private String language;

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
