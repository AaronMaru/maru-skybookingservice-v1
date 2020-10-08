package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateTaxBreakdownRS {
    private boolean included;
    private BigDecimal amount;
    private String currency;
    private String type;
    private BigDecimal clientAmount;
    private String clientCurrency;
}
