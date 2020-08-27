package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.filter;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FilterPriceCached implements Serializable {
    private BigDecimal min = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
}
