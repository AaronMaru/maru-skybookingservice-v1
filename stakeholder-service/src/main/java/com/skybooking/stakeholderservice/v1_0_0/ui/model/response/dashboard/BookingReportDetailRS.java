package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class BookingReportDetailRS {

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
