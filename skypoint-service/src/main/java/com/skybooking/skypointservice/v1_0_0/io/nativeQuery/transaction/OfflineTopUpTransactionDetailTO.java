package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OfflineTopUpTransactionDetailTO {
    protected String transactionCode;
    protected BigDecimal amount;
    protected String createdBy;
    protected Date createdAt;
}
