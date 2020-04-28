package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BookingActivityTO {
    private Integer bookingId;
    private String bookingCode;
    private String userName;
    private String userProfile;
    private String bookingType;
    private String tripType;
    private String description;
    private Timestamp createdAt;
    private String statusKey;
}
