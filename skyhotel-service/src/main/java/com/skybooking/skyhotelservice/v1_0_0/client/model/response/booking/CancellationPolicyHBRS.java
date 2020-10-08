package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CancellationPolicyHBRS {
    private BigDecimal amount = BigDecimal.ZERO;
    private String from;
    private BigDecimal percent;
}
