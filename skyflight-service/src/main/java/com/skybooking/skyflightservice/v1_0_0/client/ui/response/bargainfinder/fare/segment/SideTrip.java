package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.fare.segment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SideTrip {

    @JsonProperty("begin")
    private Boolean begin;

    @JsonProperty("end")
    private Boolean end;
}
