package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.skybooking.skyhotelservice.constant.PromotionTypeConstant;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SpecialOfferCached implements Serializable {
    private PromotionTypeConstant type;
    private BigDecimal percentage;
}
