package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OnlineTopUpTransactionDetailRS {
    private String transactionCode;
    private String transactionDate;
    private BigDecimal topUpPoint;
    private BigDecimal earnedPoint;
    private BigDecimal totalPoint;
    private BigDecimal totalPrice;
    private BigDecimal paidPrice;
    private String status;
}
