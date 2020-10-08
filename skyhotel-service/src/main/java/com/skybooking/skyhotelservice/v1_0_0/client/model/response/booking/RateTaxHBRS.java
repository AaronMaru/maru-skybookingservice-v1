package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateTaxHBRS {
    private boolean included;
    private BigDecimal percent;
    private BigDecimal amount;
    private String currency;
    private String type;
    private BigDecimal clientAmount;
    private String clientCurrency;
}
