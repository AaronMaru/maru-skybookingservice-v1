package com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff;

import java.math.BigDecimal;
import java.math.BigInteger;


public class StaffTotalBookingTO {

    private BigInteger bookingQty;
    private BigDecimal totalAmount;
    private Byte status;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

}
