package com.skybooking.skypointservice.v1_0_0.ui.model.response.topUp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUpRS {
    private String paymentMethod;
    private String transactionId;
    private String transactionDate;
    private BigDecimal pointAmount;
    private BigDecimal totalPrice;
    private String status;
}