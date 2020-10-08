package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SkyOwnerTransactionHistoryTO {
    protected String transactionCode;
    protected Integer stakeholderUserId;
    protected String transactionTypeName;
    protected BigDecimal totalPoint;
    protected BigDecimal earnedPoint;
    protected Date createdAt;
}
