package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CancellationPolicyRS {
    private BigDecimal amount = BigDecimal.ZERO;
    private String from;
}
