package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import com.skybooking.skyhotelservice.constant.PromotionTypeConstant;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpecialOfferRS {
    private PromotionTypeConstant type;
    private BigDecimal percentage;
}
