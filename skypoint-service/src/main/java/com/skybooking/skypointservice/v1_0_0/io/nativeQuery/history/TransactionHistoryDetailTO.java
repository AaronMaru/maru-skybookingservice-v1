package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class TransactionHistoryDetailTO {
    protected BigInteger id;
    protected String transactionCode;
    protected String transactionTypeCode;
    protected String transactionTypeName;
    protected String bookingId;
    protected BigDecimal topUpPoint;
    protected BigDecimal earnedPoint;
    protected BigDecimal totalPoint;
    protected BigDecimal redeemedPoint;
    protected BigDecimal totalPrice;
    protected BigDecimal paidPrice;
    protected String status;
    protected Date createdAt;
}
