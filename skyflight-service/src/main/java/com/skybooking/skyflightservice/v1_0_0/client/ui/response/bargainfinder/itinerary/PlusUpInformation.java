package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlusUpInformation {

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("fareOrigin")
    private String fareOrigin;

    @JsonProperty("fareDestination")
    private String fareDestination;

    @JsonProperty("countryOfPayment")
    private String countryOfPayment;

    @JsonProperty("viaCity")
    private String viaCity;

    @JsonProperty("message")
    private String message;
}
