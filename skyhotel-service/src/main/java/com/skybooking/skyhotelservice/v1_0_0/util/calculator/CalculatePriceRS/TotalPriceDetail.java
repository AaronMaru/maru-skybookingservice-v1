package com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalPriceDetail {
    BigDecimal amountIncludeDiscount = BigDecimal.ZERO;
    BigDecimal amountAfterDiscount = BigDecimal.ZERO;
}
