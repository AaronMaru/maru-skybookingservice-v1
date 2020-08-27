package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.account;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class AccountSummaryTO {
    protected BigInteger account;
    protected BigDecimal balance;
    protected BigDecimal earning;
}
