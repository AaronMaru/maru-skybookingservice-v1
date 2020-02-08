package com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff;

import java.math.BigDecimal;
import java.math.BigInteger;


public class StaffTotalBookingTO {

    private BigInteger quantity;
    private BigDecimal amount;
    private Byte status;

    private String statusKey;

    public StaffTotalBookingTO() {
    }

    public StaffTotalBookingTO(BigInteger quantity, BigDecimal amount, String statusKey) {
        this.quantity = quantity;
        this.amount = amount;
        this.statusKey = statusKey;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
