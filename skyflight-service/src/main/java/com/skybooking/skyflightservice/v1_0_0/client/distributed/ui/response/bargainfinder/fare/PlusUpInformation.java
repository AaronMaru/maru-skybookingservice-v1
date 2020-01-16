package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.fare;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlusUpInformation implements Serializable {

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

    @JsonProperty("message")
    private String message;

    @JsonProperty("viaCity")
    private String viaCity;

    @JsonProperty("countryOfPayment")
    private String countryOfPayment;

}
