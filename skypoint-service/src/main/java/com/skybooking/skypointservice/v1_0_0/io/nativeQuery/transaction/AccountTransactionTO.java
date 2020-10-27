package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AccountTransactionTO {
    private String transactionCode;
    private String transactionTypeCode;
    private String transactionTypeName;
    private BigDecimal amount;
    private Date createdAt;

    public AccountTransactionTO() {

    }

    public AccountTransactionTO(AccountTransactionTO accountTransactionTO) {
        transactionCode = accountTransactionTO.getTransactionCode();
        transactionTypeCode = accountTransactionTO.getTransactionTypeCode();
        transactionTypeName = accountTransactionTO.getTransactionTypeName();
        amount = AmountFormatUtil.roundAmount(accountTransactionTO.getAmount());
        createdAt = accountTransactionTO.getCreatedAt();
    }
}
