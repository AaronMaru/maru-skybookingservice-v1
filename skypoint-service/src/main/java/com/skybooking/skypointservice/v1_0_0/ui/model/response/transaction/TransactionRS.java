package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRS {
    private BigDecimal amount;
    private String description;
    private Integer stakeholderUserId;
    private Integer stakeholderCompanyId;
    private String code;
    private String name;
    private String email;
    private String phoneNumber;
}
