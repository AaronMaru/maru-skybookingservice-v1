package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

import java.util.List;

@Data
public class RateTaxesRS {
    private boolean allIncluded;
    private List<RateTaxBreakdownRS> taxes;
}
