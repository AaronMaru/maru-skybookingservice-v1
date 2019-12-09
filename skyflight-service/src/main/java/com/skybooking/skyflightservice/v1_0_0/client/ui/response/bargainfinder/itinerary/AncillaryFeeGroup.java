package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AncillaryFeeGroup {

    @JsonProperty("ancillaryFees")
    private List<AncillaryFee> ancillaryFees = new ArrayList<>();

    @JsonProperty("orderStandardBag")
    private OrderStandardBag orderStandardBag;

    @JsonProperty("message")
    private String message;
}
