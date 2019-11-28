package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class BookingTopSellerTO {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String userProfile;
    private String userCode;
    private BigDecimal totalAmount;
    private BigInteger totalBooking;

}
