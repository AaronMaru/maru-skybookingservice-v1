package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class BookingReportSummaryTO {

    private BigInteger bookingQty;
    private BigDecimal totalAmount;
    private BigDecimal paymentDiscount;
    private BigDecimal grandTotal;

}
