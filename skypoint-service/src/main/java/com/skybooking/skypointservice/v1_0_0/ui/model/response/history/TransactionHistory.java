package com.skybooking.skypointservice.v1_0_0.ui.model.response.history;

import lombok.Data;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class TransactionHistory {
    protected BigInteger id;
    protected String transactionCode;
    protected String transactionTypeCode;
    protected String transactionTypeName;
    protected BigDecimal totalPoint;
    private String createdAt;
}
