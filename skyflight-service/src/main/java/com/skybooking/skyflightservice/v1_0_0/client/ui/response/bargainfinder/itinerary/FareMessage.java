package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FareMessage {

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("code")
    private String code;

    @JsonProperty("info")
    private String info;

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;
}
