package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RateTaxBreakdownCached implements Serializable {
    private boolean included;
    private BigDecimal amount;
    private String currency;
    private String type;
    private BigDecimal clientAmount;
    private String clientCurrency;
}
