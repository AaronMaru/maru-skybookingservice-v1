package com.skybooking.skypointservice.v1_0_0.ui.model.response.history;

import com.skybooking.skypointservice.v1_0_0.io.nativeQuery.history.TransactionHistoryDetailTO;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class TransactionHistoryDetailRS {
    private BigInteger id;
    private String transactionCode;
    private String transactionTypeCode;
    private String transactionTypeName;
    private String bookingId;
    private BigDecimal topUpPoint;
    private BigDecimal earnedPoint;
    private BigDecimal totalPoint;
    private BigDecimal redeemedPoint;
    private BigDecimal totalPrice;
    private BigDecimal paidPrice;
    private String status;
    private String createdAt;
}
