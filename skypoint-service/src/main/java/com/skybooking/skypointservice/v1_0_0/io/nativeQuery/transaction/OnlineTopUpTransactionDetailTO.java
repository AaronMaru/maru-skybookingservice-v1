package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OnlineTopUpTransactionDetailTO {
    private String transactionCode;
    private String transactionDate;
    private BigDecimal pointAmount;
    private BigDecimal totalPrice;
    private BigDecimal paidPrice;
    private BigDecimal earningExtra;
    private String status;
}
