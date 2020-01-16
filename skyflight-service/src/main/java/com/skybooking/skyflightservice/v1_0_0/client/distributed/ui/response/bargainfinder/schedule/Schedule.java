package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule implements Serializable {

    @JsonProperty("ref")
    private Integer id;

    @JsonProperty("requestedStopover")
    private Boolean requestedStopover;

    @JsonProperty("departureDateAdjustment")
    private Integer departureDateAdjustment;

}
