package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard.summaryReport;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class SummaryReportTO {

    private BigInteger totalBookQty;
    private BigInteger totalStaff;
    private BigInteger lastDayBQty;
    private BigInteger todayBookQty;
    private BigDecimal lastDayBAmount;
    private BigDecimal todayBookAmount;
    private BigDecimal totalSpent;
    private String currencyCode;

}
