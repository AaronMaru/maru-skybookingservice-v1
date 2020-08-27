package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceUnitRSDS {
    private BigDecimal amount = BigDecimal.ZERO;
    private String currency;
}
