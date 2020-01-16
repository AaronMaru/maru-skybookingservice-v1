package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare.segment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Segments implements Serializable {

    @JsonProperty("segment")
    private FareSegment segment;

    @JsonProperty("surface")
    private Surface surface;
}
