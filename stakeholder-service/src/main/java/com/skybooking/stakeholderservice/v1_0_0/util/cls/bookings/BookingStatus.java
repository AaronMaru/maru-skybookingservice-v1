package com.skybooking.stakeholderservice.v1_0_0.util.cls.bookings;


import java.util.HashMap;
import java.util.Map;

public enum BookingStatus {
    COMPLETED(1),
    UPCOMING(2),
    CANCELLED(3),
    FAILED(4);

    private static Map map = new HashMap<>();

    static {
        for (BookingStatus bookingStatus : BookingStatus.values()) {
            map.put(bookingStatus.value, bookingStatus);
        }
    }

    private int value;

    BookingStatus(int value) {
        this.value = value;
    }

    public static BookingStatus valueOf(int bookingType) {
        return (BookingStatus) map.get(bookingType);
    }

    public int getValue() {
        return value;
    }
}
