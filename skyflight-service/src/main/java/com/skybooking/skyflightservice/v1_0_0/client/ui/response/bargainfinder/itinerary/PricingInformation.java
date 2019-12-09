package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricingInformation {

    @JsonProperty("poSCountryCode")
    private String poSCountryCode;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("brandsOnAnyMarket")
    private Boolean brandsOnAnyMarket;

    @JsonProperty("flexibleFare")
    private Integer flexibleFare;

    @JsonProperty("passengerGroup")
    private Integer passengerGroup;

    @JsonProperty("pricingSubsource")
    private String pricingSubsource;

    @JsonProperty("program")
    private String program;

    @JsonProperty("pseudoCityCode")
    private String pseudoCityCode;

    @JsonProperty("revalidated")
    private Boolean revalidated;

    @JsonProperty("soldOut")
    private SoldOut soldOut;

    @JsonProperty("tickets")
    private List<Ticket> tickets = new ArrayList<>();

    @JsonProperty("cached")
    private Cached cached;

    @JsonProperty("fare")
    private Fare fare;

    @JsonProperty("offer")
    private Offer offer;
}
