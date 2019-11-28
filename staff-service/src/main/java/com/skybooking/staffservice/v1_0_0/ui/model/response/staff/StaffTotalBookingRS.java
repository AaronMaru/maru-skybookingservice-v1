package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StaffTotalBookingRS {

    private BigInteger bookingQty;
    private BigDecimal totalAmount;

    private String statusKey;

    public BigInteger getBookingQty() {
        return bookingQty;
    }

    public void setBookingQty(BigInteger bookingQty) {
        this.bookingQty = bookingQty;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

}
