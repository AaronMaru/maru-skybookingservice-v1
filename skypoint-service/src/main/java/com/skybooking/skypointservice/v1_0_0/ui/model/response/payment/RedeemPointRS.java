package com.skybooking.skypointservice.v1_0_0.ui.model.response.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RedeemPointRS {
    private String transactionCode;
    private BigDecimal redeem;
    private BigDecimal balance;
    private BigDecimal savedPoint;
    private String levelName;
}
