package com.skybooking.skypointservice.v1_0_0.ui.model.response.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceRS {

    protected BigDecimal topup;
    protected BigDecimal withdrawal;
    protected BigDecimal earning;
    protected BigDecimal earningExtra;
    protected BigDecimal refund;
    protected BigDecimal balance;
    protected String levelName;
    protected String status;

}
