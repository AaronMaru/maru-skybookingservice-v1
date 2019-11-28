package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookingReportRS {

    private BigInteger bookingQty;
    private BigDecimal totalAmount;
    private BigDecimal paymentDiscount;
    private BigDecimal grandTotal;
    private List<BookingReportDetailRS> bookings = new ArrayList<>();

}
