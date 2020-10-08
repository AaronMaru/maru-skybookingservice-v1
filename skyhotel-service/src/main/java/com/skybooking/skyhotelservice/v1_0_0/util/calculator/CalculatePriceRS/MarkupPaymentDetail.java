package com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MarkupPaymentDetail {
    BigDecimal markupPercentage = BigDecimal.ZERO;
    BigDecimal markupAmount = BigDecimal.ZERO;
}
