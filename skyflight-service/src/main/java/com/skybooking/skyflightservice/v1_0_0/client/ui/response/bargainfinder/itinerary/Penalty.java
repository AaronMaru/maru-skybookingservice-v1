package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Penalty {

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("applicability")
    private String applicability;

    @JsonProperty("cat16Info")
    private Boolean cat16Info;

    @JsonProperty("cat16TextOnly")
    private List<Cat16TextOnly> cat16TextOnly = new ArrayList<>();

    @JsonProperty("changeable")
    private Boolean changeable;

    @JsonProperty("conditionsApply")
    private Boolean conditionsApply;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("refundable")
    private Boolean refundable;

    @JsonProperty("type")
    private String type;
}
