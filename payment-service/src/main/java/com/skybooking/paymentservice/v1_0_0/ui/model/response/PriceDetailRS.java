package com.skybooking.paymentservice.v1_0_0.ui.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceDetailRS {

    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal paidAmount;
    private String currency = "USD";

    public PriceDetailRS(BigDecimal totalAmount, BigDecimal discount, BigDecimal paidAmount) {
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.paidAmount = paidAmount;
    }
}
