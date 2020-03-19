package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.report;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookingReportRS {

    private BigInteger bookingQty;
    private BigDecimal totalPassenger = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal paymentDiscount = BigDecimal.ZERO;
    private BigDecimal grandTotal = BigDecimal.ZERO;
    private List<BookingReportDetailRS> bookings = new ArrayList<>();

}
