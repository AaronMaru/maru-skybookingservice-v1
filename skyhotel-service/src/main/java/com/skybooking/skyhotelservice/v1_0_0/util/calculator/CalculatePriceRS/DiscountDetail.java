package com.skybooking.skyhotelservice.v1_0_0.util.calculator.CalculatePriceRS;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate.Discount;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DiscountDetail {
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal unitDiscount = BigDecimal.ZERO;
    private List<Discount> discounts;
}

