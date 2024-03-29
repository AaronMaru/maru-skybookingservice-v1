package com.skybooking.skypointservice.v1_0_0.ui.model.response.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RedeemPointConfirmRS {
    private String transactionCode;
    private BigDecimal redeem;
    private BigDecimal balance;
    private BigDecimal savedPoint;
    private String levelCode;
    private String levelName;
}
