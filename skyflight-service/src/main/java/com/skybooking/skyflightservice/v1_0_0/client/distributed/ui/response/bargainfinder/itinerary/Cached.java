package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cached implements Serializable {

    @JsonProperty("ref")
    private Integer id;

    @JsonProperty("hoursSinceCreation")
    private Integer hoursSinceCreation;

    @JsonProperty("timeToLive")
    private Integer timeToLive;

}
