package com.skybooking.skypointservice.v1_0_0.io.nativeQuery.transaction;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AmountOnlineTopUpTO {
    private Integer accountId;
    private BigDecimal amount = BigDecimal.valueOf(0);

}
