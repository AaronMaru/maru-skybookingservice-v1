package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OnlineTopUpTransactionDetailTO {
    protected String transactionCode;
    protected String transactionDate;
    protected BigDecimal topUpPoint;
    protected BigDecimal earnedPoint;
    protected BigDecimal totalPoint;
    protected BigDecimal totalPrice;
    protected BigDecimal paidPrice;
    protected String status = "Successfully";
}
