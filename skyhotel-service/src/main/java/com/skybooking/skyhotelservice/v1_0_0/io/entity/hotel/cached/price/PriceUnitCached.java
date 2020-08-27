package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.price;

import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PriceUnitCached implements Serializable {

    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal amountAfterDiscount = BigDecimal.ZERO;
    private float discountPercentage = 0;
    private String currency = CurrencyConstant.USD.CODE;

}
