package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleMessage implements Serializable {

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("pricingSource")
    private String pricingSource;

    @JsonProperty("type")
    private String type;

    @JsonProperty("text")
    private String text;

}
