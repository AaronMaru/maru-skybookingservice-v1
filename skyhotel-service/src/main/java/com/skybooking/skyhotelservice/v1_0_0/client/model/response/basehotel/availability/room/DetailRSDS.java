package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetailRSDS {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal netAmount = BigDecimal.ZERO;
    private BigDecimal totalTaxesAmount = BigDecimal.ZERO;
    private String currency;

}

