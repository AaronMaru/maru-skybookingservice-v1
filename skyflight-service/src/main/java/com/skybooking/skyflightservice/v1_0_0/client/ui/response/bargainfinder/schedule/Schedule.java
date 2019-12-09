package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule {

    @JsonProperty("ref")
    private Integer id;

    @JsonProperty("requestedStopover")
    private Boolean requestedStopover;

    @JsonProperty("departureDateAdjustment")
    private Integer departureDateAdjustment;

}
