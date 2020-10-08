package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

import java.util.List;

@Data
public class CheckRateRateTaxesRS {
    private boolean allIncluded;
    private List<RateTaxRS> taxes;
}
