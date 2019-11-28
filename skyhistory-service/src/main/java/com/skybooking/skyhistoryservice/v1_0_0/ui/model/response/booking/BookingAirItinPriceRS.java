package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import java.math.BigDecimal;

public class BookingAirItinPriceRS {

    private String passType;
    private Integer passQty;
    private BigDecimal baseFare;
    private BigDecimal totalTax;
    private BigDecimal totalAmount;
    private String currencyCode;
    private String nonRefund;

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public Integer getPassQty() {
        return passQty;
    }

    public void setPassQty(Integer passQty) {
        this.passQty = passQty;
    }

    public BigDecimal getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(BigDecimal baseFare) {
        this.baseFare = baseFare;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getNonRefund() {
        return nonRefund;
    }

    public void setNonRefund(String nonRefund) {
        this.nonRefund = nonRefund;
    }


}
