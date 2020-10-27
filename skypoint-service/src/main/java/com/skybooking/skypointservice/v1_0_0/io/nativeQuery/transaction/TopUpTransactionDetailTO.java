package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TopUpTransactionDetailTO {
    protected String transactionCode;
    protected String userCode;
    protected String userType;
    protected BigDecimal amount;
    protected String createdBy;
    protected Date createdAt;

    public TopUpTransactionDetailTO() {

    }

    public TopUpTransactionDetailTO(TopUpTransactionDetailTO topUpTransactionDetailTO) {
        transactionCode = topUpTransactionDetailTO.getTransactionCode();
        userCode = topUpTransactionDetailTO.getUserCode();
        userType = topUpTransactionDetailTO.getUserType();
        amount = AmountFormatUtil.roundAmount(topUpTransactionDetailTO.getAmount());
        createdAt = topUpTransactionDetailTO.getCreatedAt();
        createdBy = topUpTransactionDetailTO.getCreatedBy();
    }
}
