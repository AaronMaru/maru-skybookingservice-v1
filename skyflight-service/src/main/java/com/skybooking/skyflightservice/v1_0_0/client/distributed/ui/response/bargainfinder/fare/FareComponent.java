package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.brand.Brand;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare.segment.Segments;
import com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.other.DirectionEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FareComponent implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("applicablePricingCategories")
    private String applicablePricingCategories;

    @JsonProperty("aslRetailerRuleCode")
    private String aslRetailerRuleCode;

    @JsonProperty("brand")
    private Brand brand;

    @JsonProperty("corporateIdMatched")
    private Boolean corporateIdMatched;

    @JsonProperty("direction")
    private DirectionEnum direction;

    @JsonProperty("directionality")
    private String directionality;

    @JsonProperty("displayCategoryType")
    private String displayCategoryType;

    @JsonProperty("fareAmount")
    private BigDecimal amount;

    @JsonProperty("fareBasisCode")
    private String basisCode;

    @JsonProperty("fareComponentReferenceId")
    private Integer referenceId;

    @JsonProperty("fareCurrency")
    private String currency;

    @JsonProperty("farePassengerType")
    private String passengerType;

    @JsonProperty("fareTypeBitmap")
    private String typeBitmap;

    @JsonProperty("generalRetailerRuleCode")
    private String generalRetailerRuleCode;

    @JsonProperty("governingCarrier")
    private String governingCarrier;

    @JsonProperty("handlingMarkupDetails")
    private List<HandlingMarkupDetail> handlingMarkupDetails = new ArrayList<>();

    @JsonProperty("higherIntermediatePoints")
    private List<HigherIntermediatePoint> higherIntermediatePoints = new ArrayList<>();

    @JsonProperty("matchedAccountCode")
    private String matchedAccountCode;

    @JsonProperty("mileage")
    private Boolean mileage;

    @JsonProperty("mileageSurcharge")
    private Integer mileageSurcharge;

    @JsonProperty("milesAmount")
    private Integer milesAmount;

    @JsonProperty("negotiatedFare")
    private Boolean negotiatedFare;

    @JsonProperty("notValidAfter")
    private String notValidAfter;

    @JsonProperty("notValidBefore")
    private String notValidBefore;

    @JsonProperty("oneWayFare")
    private Boolean oneWayFare;

    @JsonProperty("plusUps")
    private List<PlusUpInformation> plusUps = new ArrayList<>();

    @JsonProperty("privateFare")
    private Boolean privateFare;

    @JsonProperty("publishedFareAmount")
    private BigDecimal publishedFareAmount;

    @JsonProperty("segments")
    private List<Segments> segments = new ArrayList<>();

    @JsonProperty("ticketDesignator")
    private String ticketDesignator;

    @JsonProperty("vendorCode")
    private String vendorCode;

    @JsonProperty("webBasedFare")
    private Boolean webBasedFare;


}
