package com.skybooking.skypointservice.v1_0_0.ui.model.response.limitPoint;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AvailablePointRS {
    private BigDecimal availablePoint;
    private BigDecimal usedPoint;
    private BigDecimal limitPoint;
}
