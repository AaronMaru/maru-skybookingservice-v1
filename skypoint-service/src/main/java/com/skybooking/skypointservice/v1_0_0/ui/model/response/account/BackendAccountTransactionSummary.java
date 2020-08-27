package com.skybooking.skypointservice.v1_0_0.ui.model.response.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BackendAccountTransactionSummary {
    private BigDecimal topUp;
    private BigDecimal earning;
    private BigDecimal redeem;
}
