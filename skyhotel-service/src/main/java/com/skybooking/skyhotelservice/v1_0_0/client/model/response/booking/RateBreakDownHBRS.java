package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import lombok.Data;

import java.util.List;

@Data
public class RateBreakDownHBRS {
    private List<RateDiscountHBRS> rateDiscounts;
    private List<RateSupplementHBRS> rateSupplements;
}
