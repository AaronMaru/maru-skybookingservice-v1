package com.skybooking.skypointservice.v1_0_0.ui.model.response.history;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkyOwnerTransactionHistory {
    private String transactionCode;
    private Integer stakeholderUserId;
    private String transactionTypeName;
    private BigDecimal totalPoint;
    private String createdAt;
}
