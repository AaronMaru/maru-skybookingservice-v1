package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.price;

import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceUnitRS {

    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal amountAfterDiscount = BigDecimal.ZERO;
    private float discountPercentage = 0;
    private String currency = CurrencyConstant.USD.CODE;

}