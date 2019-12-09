package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FareComponentBreakdown {

    @JsonProperty("contractFamilyId")
    private Integer contractFamilyId;

    @JsonProperty("contractId")
    private Integer contractId;

    @JsonProperty("earnedFareComponentCommission")
    private BigDecimal earnedFareComponentCommission;

    @JsonProperty("fareComponentCommission")
    private BigDecimal fareComponentCommission;

    @JsonProperty("fareComponentReferenceId")
    private Integer fareComponentReferenceId;

    @JsonProperty("methodId")
    private Integer methodId;

    @JsonProperty("ruleFamilyId")
    private Integer ruleFamilyId;

    @JsonProperty("ruleId")
    private Integer ruleId;

}
