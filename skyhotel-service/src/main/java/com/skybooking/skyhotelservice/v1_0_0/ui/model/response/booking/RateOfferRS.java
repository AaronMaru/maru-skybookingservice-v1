package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateOfferRS {
    private String code;
    private String name;
    private BigDecimal amount = BigDecimal.ZERO;
}
