package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare.segment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SideTrip implements Serializable {

    @JsonProperty("begin")
    private Boolean begin;

    @JsonProperty("end")
    private Boolean end;
}
