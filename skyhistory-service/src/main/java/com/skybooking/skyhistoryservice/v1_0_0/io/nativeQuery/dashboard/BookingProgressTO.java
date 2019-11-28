package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.math.BigInteger;

@Data
public class BookingProgressTO {
    private String status;
    private BigInteger amount;
}
