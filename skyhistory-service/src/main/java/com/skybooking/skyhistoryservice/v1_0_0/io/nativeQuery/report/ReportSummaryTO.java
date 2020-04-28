package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class ReportSummaryTO {

    private BigInteger bookingQty;
    private BigDecimal totalPassenger = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal paidAmount = BigDecimal.ZERO;
    private String currencyCode;

}
