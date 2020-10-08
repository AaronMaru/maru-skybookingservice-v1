package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RateBreakDownCached implements Serializable {
    private List<RateDiscountCached> rateDiscounts;
    private List<RateSupplementCached> rateSupplements;
}
