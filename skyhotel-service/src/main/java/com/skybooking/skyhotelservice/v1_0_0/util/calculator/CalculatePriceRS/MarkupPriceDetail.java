package com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MarkupPriceDetail {
    BigDecimal amount = BigDecimal.ZERO;
    BigDecimal markupAmount = BigDecimal.ZERO;
    BigDecimal totalMarkupAmount = BigDecimal.ZERO;
    BigDecimal markupPercentage = BigDecimal.ZERO;
}
