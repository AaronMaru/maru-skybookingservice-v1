package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import lombok.Data;

import java.util.List;

@Data
public class RateTaxesHBRS {
    private boolean allIncluded;
    private List<RateTaxHBRS> taxes;
}
