package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AncillaryFee {

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("message")
    private String message;

    @JsonProperty("details")
    private List<AncillaryFeeDetails> details = new ArrayList<>();
}
