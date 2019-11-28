package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class BookingReportDetailTO {

    private Integer bookingId;
    private String bookingCode;
    private String prnCode;
    private String tripType;
    private String className;
    private BigInteger totalPass;
    private BigDecimal totalCost;
    private BigDecimal paymentDiscount;
    private BigDecimal grandTotal;

}
