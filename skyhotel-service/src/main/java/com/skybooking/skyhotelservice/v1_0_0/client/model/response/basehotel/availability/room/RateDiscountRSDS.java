package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room;

import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateDiscountRSDS {
    private String code;
    private String name;
    private BigDecimal amount = BigDecimal.ZERO;
    private String currency = CurrencyConstant.USD.CODE;
}
