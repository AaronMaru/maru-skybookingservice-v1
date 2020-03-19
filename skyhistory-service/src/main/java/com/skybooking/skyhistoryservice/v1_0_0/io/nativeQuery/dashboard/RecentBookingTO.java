package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class RecentBookingTO {

    private Integer bookingId;
    private String bookingCode;
    private String TripType;
    private Integer skyuserId;
    private String skyuserName;
    private String skyuserPhoto;
    private BigDecimal totalAmount;
    private Timestamp createdAt;
    private String currencyCode;

}
