package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class ReportChartTO {

    private BigInteger bookingQty = BigInteger.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private String currencyCode;
    private Date createdAt;


}
