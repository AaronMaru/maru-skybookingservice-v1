package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.skyPointTransaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionAmountTO {
    private BigDecimal amount = BigDecimal.valueOf(0);
}
