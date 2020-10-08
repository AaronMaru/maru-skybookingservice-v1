package com.skybooking.skypointservice.v1_0_0.ui.model.response.refund;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundRS {
    private BigDecimal refundAmount;
    private BigDecimal balance;
    private BigDecimal savedPoint;
    private BigDecimal earning;
    private BigDecimal earningExtra;
}
