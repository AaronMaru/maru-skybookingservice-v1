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
public class ValidatingCarrierCommissionInfo implements Serializable {

    @JsonProperty("commissionAmount")
    private BigDecimal commissionAmount;

    @JsonProperty("commissionContractQualifier")
    private String commissionContractQualifier;

    @JsonProperty("commissionPercent")
    private BigDecimal commissionPercent;

    @JsonProperty("earnedCommissionAmount")
    private BigDecimal earnedCommissionAmount;

    @JsonProperty("fareComponentBreakdowns")
    private List<FareComponentBreakdown> fareComponentBreakdowns = new ArrayList<>();

    @JsonProperty("sourcePcc")
    private String sourcePcc;

    @JsonProperty("totalAmountIncludingMarkUp")
    private BigDecimal totalAmountIncludingMarkUp;

    @JsonProperty("validatingCarrier")
    private String validatingCarrier;
}
