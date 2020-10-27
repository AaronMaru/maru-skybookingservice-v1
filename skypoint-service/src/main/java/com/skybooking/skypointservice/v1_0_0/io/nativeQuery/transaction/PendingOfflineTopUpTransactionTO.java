package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

@Data
public class PendingOfflineTopUpTransactionTO extends TopUpTransactionDetailTO {
    private String userCode;

    public PendingOfflineTopUpTransactionTO() {

    }

    public PendingOfflineTopUpTransactionTO(PendingOfflineTopUpTransactionTO pendingOfflineTopUpTransactionTO) {
        userCode = pendingOfflineTopUpTransactionTO.getUserCode();
        transactionCode = pendingOfflineTopUpTransactionTO.getTransactionCode();
        amount = AmountFormatUtil.roundAmount(pendingOfflineTopUpTransactionTO.getAmount());
        createdAt = pendingOfflineTopUpTransactionTO.getCreatedAt();
        createdBy = pendingOfflineTopUpTransactionTO.getCreatedBy();
    }
}
