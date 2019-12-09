package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HandlingMarkup {

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("description")
    private String description;

    @JsonProperty("extendedDescription")
    private String extendedDescription;

    @JsonProperty("fareRetailerRule")
    private Boolean fareRetailerRule;

    @JsonProperty("hiddenHandlingFee")
    private Boolean hiddenHandlingFee;

    @JsonProperty("nonHiddenHandlingFee")
    private Boolean nonHiddenHandlingFee;

    @JsonProperty("type")
    private String type;
}
