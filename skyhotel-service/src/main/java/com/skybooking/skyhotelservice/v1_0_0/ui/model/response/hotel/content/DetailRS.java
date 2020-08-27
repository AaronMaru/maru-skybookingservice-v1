package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailRS {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal netAmount = BigDecimal.ZERO;
    private BigDecimal totalTaxesAmount = BigDecimal.ZERO;
    private String currency;
}
