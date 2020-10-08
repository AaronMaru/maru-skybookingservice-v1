package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceTO {

    private String userCode;
    private String type;
    private BigDecimal topup;
    private BigDecimal withdrawal;
    private BigDecimal earning;
    private BigDecimal earningExtra;
    private BigDecimal refund;
    private BigDecimal balance;
    private BigDecimal savedPoint;
    private String levelName;
    private String status;

}
