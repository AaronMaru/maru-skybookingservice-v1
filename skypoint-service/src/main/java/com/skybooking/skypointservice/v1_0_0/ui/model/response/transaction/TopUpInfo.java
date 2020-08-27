package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TopUpInfo {
    private String transactionCode;
    private BigDecimal amount;
    private String createdBy;
    private Date createdAt;
}
