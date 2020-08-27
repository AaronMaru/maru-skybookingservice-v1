package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountTransactionTO {
    private String transactionTypeName;
    private BigDecimal amount;
    private Date createdAt;
}
