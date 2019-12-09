package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.statistics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Statistics {

    @JsonProperty("branded")
    private Integer branded;

    @JsonProperty("departed")
    private Integer departed;

    @JsonProperty("itineraryCount")
    private Integer itineraryCount;

    @JsonProperty("legMissed")
    private Integer legMissed;

    @JsonProperty("oneWay")
    private Integer oneWay;

    @JsonProperty("soldOut")
    private Integer soldOut;
}
