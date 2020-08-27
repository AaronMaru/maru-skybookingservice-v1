package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DetailCached implements Serializable {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal netAmount = BigDecimal.ZERO;
    private BigDecimal totalTaxesAmount = BigDecimal.ZERO;
    private String currency;
}
