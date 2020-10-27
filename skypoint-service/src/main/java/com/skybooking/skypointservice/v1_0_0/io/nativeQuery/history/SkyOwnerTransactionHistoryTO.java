package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SkyOwnerTransactionHistoryTO {
    protected String transactionCode;
    protected Integer stakeholderUserId;
    protected String transactionTypeName;
    protected BigDecimal totalPoint;
    protected Date createdAt;

    public SkyOwnerTransactionHistoryTO() {

    }

    public SkyOwnerTransactionHistoryTO(SkyOwnerTransactionHistoryTO skyOwnerTransactionHistoryTO) {
        transactionCode = skyOwnerTransactionHistoryTO.getTransactionCode();
        stakeholderUserId = skyOwnerTransactionHistoryTO.getStakeholderUserId();
        transactionTypeName = skyOwnerTransactionHistoryTO.getTransactionTypeName();
        totalPoint = AmountFormatUtil.roundAmount(skyOwnerTransactionHistoryTO.getTotalPoint());
        createdAt = skyOwnerTransactionHistoryTO.getCreatedAt();
    }
}
