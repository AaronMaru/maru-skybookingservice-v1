package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellingFareData {

    @JsonProperty("baseFareAmount")
    private BigDecimal baseFareAmount;

    @JsonProperty("constructedTotalAmount")
    private BigDecimal constructedTotalAmount;

    @JsonProperty("equivalentAmount")
    private BigDecimal equivalentAmount;

    @JsonProperty("fareCalculation")
    private String fareCalculation;

    @JsonProperty("fareRetailerRule")
    private Boolean fareRetailerRule;

    @JsonProperty("handlingMarkups")
    private List<HandlingMarkup> handlingMarkups = new ArrayList<>();

    @JsonProperty("taxSummaries")
    private List<TaxSummaryID> taxSummaries = new ArrayList<>();

    @JsonProperty("taxes")
    private List<TaxID> taxes = new ArrayList<>();

    @JsonProperty("totalPerPassenger")
    private BigDecimal totalPerPassenger;

    @JsonProperty("totalTaxAmount")
    private BigDecimal totalTaxAmount;

    @JsonProperty("type")
    private String type;

}
