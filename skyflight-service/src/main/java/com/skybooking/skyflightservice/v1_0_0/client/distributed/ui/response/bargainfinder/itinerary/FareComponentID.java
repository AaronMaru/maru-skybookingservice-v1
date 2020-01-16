package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FareComponentID implements Serializable {

    @JsonProperty("ref")
    private Integer id;

    @JsonProperty("brandFeatures")
    private List<BrandFeatureID> brandFeatures = new ArrayList<>();

    @JsonProperty("effectivePriceDeviation")
    private BigDecimal effectivePriceDeviation;

    @JsonProperty("segments")
    private List<FareComponentSegment> segments = new ArrayList<>();

    @JsonProperty("taxSummaries")
    private List<TaxSummaryID> taxSummaries = new ArrayList<>();

    @JsonProperty("taxes")
    private List<TaxID> taxes = new ArrayList<>();

    @JsonProperty("totalFare")
    private TotalFare totalFare;
}
