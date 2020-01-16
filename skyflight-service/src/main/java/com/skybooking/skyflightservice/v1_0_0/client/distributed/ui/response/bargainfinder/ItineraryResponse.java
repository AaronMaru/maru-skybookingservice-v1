package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.baggage.allowance.BaggageAllowance;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.baggage.charge.BaggageCharge;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.brand.BrandFeature;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.cache.CacheSource;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare.FareComponent;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary.ItineraryGroupType;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.leg.Leg;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.message.Message;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.obfee.OBFee;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.ScheduleComponent;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule.ScheduleMessage;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.statistics.Statistics;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.tax.Tax;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.tax.TaxSummary;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.validating.ValidatingCarrierType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryResponse implements Serializable {

    @JsonProperty("version")
    private String version;

    @JsonProperty("baggageAllowanceDescs")
    private List<BaggageAllowance> baggageAllowances = new ArrayList<>();

    @JsonProperty("baggageChargeDescs")
    private List<BaggageCharge> baggageCharges = new ArrayList<>();

    @JsonProperty("brandFeatureDescs")
    private List<BrandFeature> brandFeatures = new ArrayList<>();

    @JsonProperty("cacheSourceDescs")
    private List<CacheSource> cacheSources = new ArrayList<>();

    @JsonProperty("fareComponentDescs")
    private List<FareComponent> fareComponents = new ArrayList<>();

    @JsonProperty("legDescs")
    private List<Leg> legs = new ArrayList<>();

    @JsonProperty("messages")
    private List<Message> messages = new ArrayList<>();

    @JsonProperty("obFeeDescs")
    private List<OBFee> obFees = new ArrayList<>();

    @JsonProperty("scheduleDescs")
    private List<ScheduleComponent> scheduleComponents = new ArrayList<>();

    @JsonProperty("scheduleMessages")
    private List<ScheduleMessage> scheduleMessages = new ArrayList<>();

    @JsonProperty("taxDescs")
    private List<Tax> taxes = new ArrayList<>();

    @JsonProperty("taxSummaryDescs")
    private List<TaxSummary> taxSummaries = new ArrayList<>();

    @JsonProperty("statistics")
    private Statistics statistics;

    @JsonProperty("validatingCarrierDescs")
    private List<ValidatingCarrierType> validatingCarrierTypes = new ArrayList<>();

    @JsonProperty("itineraryGroups")
    private List<ItineraryGroupType> itineraryGroups = new ArrayList<>();

}
