package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RateTaxesCached implements Serializable {
    private boolean allIncluded;
    private List<RateTaxBreakdownCached> taxes;
}
