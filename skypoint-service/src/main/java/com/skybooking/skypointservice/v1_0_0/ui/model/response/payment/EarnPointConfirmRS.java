package com.skybooking.skypointservice.v1_0_0.ui.model.response.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EarnPointConfirmRS {
    private BigDecimal amount;
    private BigDecimal earning;
    private BigDecimal savedPoint;
    private BigDecimal balance;
    private String levelCode;
    private String levelName;
}
