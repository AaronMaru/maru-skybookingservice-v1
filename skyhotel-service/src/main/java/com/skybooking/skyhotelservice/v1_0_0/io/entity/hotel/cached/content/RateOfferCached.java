package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RateOfferCached implements Serializable {
    private String code;
    private String name;
    private BigDecimal amount = BigDecimal.ZERO;
    private String currency = CurrencyConstant.USD.CODE;
}
