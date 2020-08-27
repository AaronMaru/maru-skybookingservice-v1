package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FeeCached implements Serializable {
    private BigDecimal amount = BigDecimal.ZERO;
    private String currency;
    private String from;
}
