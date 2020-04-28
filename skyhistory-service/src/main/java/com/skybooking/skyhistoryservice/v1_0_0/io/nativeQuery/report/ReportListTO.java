package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReportListTO {

    private Integer bookingId;
    private String bookingCode;
    private String prnCode;
    private String tripType;
    private String currencyCode;
    private String className;
    private String bookedBy;
    private BigDecimal totalPassenger = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal paidAmount = BigDecimal.ZERO;
}
