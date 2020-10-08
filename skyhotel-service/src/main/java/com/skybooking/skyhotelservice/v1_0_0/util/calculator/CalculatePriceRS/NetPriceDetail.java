package com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NetPriceDetail {
    BigDecimal totalNet = BigDecimal.ZERO;
    BigDecimal unitNet = BigDecimal.ZERO;
}
