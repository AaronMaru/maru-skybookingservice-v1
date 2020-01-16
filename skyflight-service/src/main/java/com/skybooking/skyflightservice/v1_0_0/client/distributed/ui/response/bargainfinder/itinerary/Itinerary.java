package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Itinerary implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("currentItinerary")
    private Boolean currentItinerary;

    @JsonProperty("diversitySwapper")
    private DiversitySwapper diversitySwapper;

    @JsonProperty("failed")
    private Failed failed;

    @JsonProperty("itinSource")
    private String itinSource;

    @JsonProperty("legs")
    private List<LegID> legs = new ArrayList<>();

    @JsonProperty("pricingInformation")
    private List<PricingInformation> pricingInformation = new ArrayList<>();

    @JsonProperty("pricingSource")
    private String pricingSource;

    @JsonProperty("routingItinerary")
    private Boolean routingItinerary;
}
