package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.account;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopBalanceTO {
    private String userCode;
    private String levelName;
    private BigDecimal balance;
}
