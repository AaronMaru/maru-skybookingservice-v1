package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Offer {

    @JsonProperty("offerId")
    private String offerId;

    @JsonProperty("source")
    private String source;

    @JsonProperty("ttl")
    private Integer ttl;
}
