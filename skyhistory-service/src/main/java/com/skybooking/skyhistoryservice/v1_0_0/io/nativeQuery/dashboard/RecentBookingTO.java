package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class RecentBookingTO {

    private Integer bookingId;
    private String bookingCode;
    private String TripType;
    private String contName;
    private String contPhoto;
    private BigDecimal totalAmount;
    private Timestamp createdAt;

}
