package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.fare;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HigherIntermediatePoint {

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("cabinHigh")
    private String cabinHigh;

    @JsonProperty("cabinLow")
    private String cabinLow;

    @JsonProperty("fareClassHigh")
    private String fareClassHigh;

    @JsonProperty("fareClassLow")
    private String fareClassLow;

    @JsonProperty("highDestination")
    private String highDestination;

    @JsonProperty("highOrigin")
    private String highOrigin;

    @JsonProperty("lowDestination")
    private String lowDestination;

    @JsonProperty("lowOrigin")
    private String lowOrigin;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("mileageSurchargePercentage")
    private Integer mileageSurchargePercentage;
}
