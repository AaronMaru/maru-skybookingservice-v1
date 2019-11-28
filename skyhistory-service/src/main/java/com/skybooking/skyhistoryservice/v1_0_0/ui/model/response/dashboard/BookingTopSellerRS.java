package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class BookingTopSellerRS {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String userCode;
    private String userProfile;
    private BigDecimal totalAmount;
    private BigInteger totalBooking;
}
