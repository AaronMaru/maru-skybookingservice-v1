package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OfflineTopUpTransactionDetailTO {
    private String transactionCode;
    private BigDecimal amount;
    private String createdBy;
    private Date createdAt;
}
