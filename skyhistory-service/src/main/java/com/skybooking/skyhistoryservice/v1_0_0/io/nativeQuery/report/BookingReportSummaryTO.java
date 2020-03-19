package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.report;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class BookingReportSummaryTO {

    private BigInteger bookingQty;
    private BigDecimal totalPassenger = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal paymentDiscount = BigDecimal.ZERO;
    private BigDecimal grandTotal = BigDecimal.ZERO;

}
