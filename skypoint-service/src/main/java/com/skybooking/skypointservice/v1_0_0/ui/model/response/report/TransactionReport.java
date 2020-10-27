package com.skybooking.skypointservice.v1_0_0.ui.model.response.report;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionReport {
    private String transactionCode;
    private String userCode;
    private String accountName;
    private String transactionTypeCode;
    private String transactionTypeName;
    private BigDecimal amount;
    private String transactionDate;
    private String remark;
    private String referenceCode;
}
