package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class TransactionHistoryTO {
    protected BigInteger id;
    protected String transactionCode;
    protected String transactionTypeCode;
    protected String transactionTypeName;
    protected BigDecimal totalPoint;
    private Date createdAt;
}
