package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricingLeg {

    @JsonProperty("ref")
    private Integer id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("taxSummaries")
    private List<TaxSummaryID> taxSummaries = new ArrayList<>();

    @JsonProperty("taxes")
    private List<TaxID> taxes = new ArrayList<>();

    @JsonProperty("totalFare")
    private TotalFare totalFare;
}
