package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import com.skybooking.skypointservice.util.AmountFormatUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OnlineTopUpTransactionDetailTO {
    private String transactionCode;
    private Date transactionDate;
    private BigDecimal topUpPoint;
    private BigDecimal earnedPoint;
    private BigDecimal totalPoint;
    private BigDecimal totalPrice;
    private BigDecimal paidPrice;
    private String status;

    public OnlineTopUpTransactionDetailTO() {

    }

    public OnlineTopUpTransactionDetailTO(OnlineTopUpTransactionDetailTO onlineTopUpTransactionDetailTO) {
        transactionCode = onlineTopUpTransactionDetailTO.getTransactionCode();
        transactionDate = onlineTopUpTransactionDetailTO.getTransactionDate();
        topUpPoint = AmountFormatUtil.roundAmount(onlineTopUpTransactionDetailTO.getTopUpPoint());
        earnedPoint = AmountFormatUtil.roundAmount(onlineTopUpTransactionDetailTO.getEarnedPoint());
        totalPoint = AmountFormatUtil.roundAmount(onlineTopUpTransactionDetailTO.getTotalPoint());
        totalPrice = AmountFormatUtil.roundAmount(onlineTopUpTransactionDetailTO.getTotalPrice());
        paidPrice = AmountFormatUtil.roundAmount(onlineTopUpTransactionDetailTO.getPaidPrice());
        status = onlineTopUpTransactionDetailTO.getStatus();
    }

}
