package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.properties.room;

import lombok.Data;

import java.util.List;

@Data
public class RateTaxRSDS {
    private boolean allIncluded;
    private List<TaxBreakdownRSDS> taxes;
}
