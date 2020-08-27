package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class TransactionHistoryTO {
    private BigInteger id;
    private String transactionCode;
    private String transactionTypeName;
    private BigDecimal amount;
    private String createdAt;
}
