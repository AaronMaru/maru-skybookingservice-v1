package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class TransactionHistoryDetailTO {
    protected BigInteger id;
    protected String transactionId;
    protected String transactionTypeName;
    protected String bookingId;
    protected BigDecimal pointAmount;
    protected BigDecimal earning;
    protected BigDecimal amount;
    protected BigDecimal totalPrice;
    protected String status;
    protected String createdAt;
}
