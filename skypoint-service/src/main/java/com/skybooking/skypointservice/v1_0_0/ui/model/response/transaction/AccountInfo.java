package com.skybooking.skypointservice.v1_0_0.ui.model.response.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountInfo {
    private String levelName = "Blue";
    private BigDecimal earning = BigDecimal.valueOf(0);
    private BigDecimal earningExtra = BigDecimal.valueOf(0);
    private BigDecimal savedPoint = BigDecimal.valueOf(0);
    private BigDecimal balance = BigDecimal.valueOf(0);
}
