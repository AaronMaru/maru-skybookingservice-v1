package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassengerInformation {

    @JsonProperty("baggageInformation")
    private List<BaggageInformation> baggageInformation = new ArrayList<>();

    @JsonProperty("currencyConversion")
    private CurrencyConversion currencyConversion;

    @JsonProperty("divide")
    private Boolean divide;

    @JsonProperty("fareComponents")
    private List<FareComponentID> fareComponents = new ArrayList<>();

    @JsonProperty("fareMessages")
    private List<FareMessage> fareMessages = new ArrayList<>();

    @JsonProperty("legs")
    private List<PricingLeg> legs = new ArrayList<>();

    @JsonProperty("nonRefundable")
    private Boolean nonRefundable;

    @JsonProperty("obFees")
    private List<OBFeeID> obFees = new ArrayList<>();

    @JsonProperty("passengerNumber")
    private Integer passengerNumber;

    @JsonProperty("passengerTotalFare")
    private PassengerTotalFare passengerTotalFare;

    @JsonProperty("passengerType")
    private String passengerType;

    @JsonProperty("penaltiesInfo")
    private Penalties penaltiesInfo;

    @JsonProperty("plusUps")
    private List<PlusUpInformation> plusUps = new ArrayList<>();

    @JsonProperty("reissue")
    private Reissue reissue;

    @JsonProperty("sellingFareData")
    private List<SellingFareData> sellingFareData = new ArrayList<>();

    @JsonProperty("stopover")
    private Integer stopover;

    @JsonProperty("taxSummaries")
    private List<TaxSummaryID> taxSummaries = new ArrayList<>();

    @JsonProperty("taxes")
    private List<TaxID> taxes = new ArrayList<>();

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("validatingCarrierCommissionInfo")
    private List<ValidatingCarrierCommissionInfo> validatingCarrierCommissionInfo = new ArrayList<>();

}
