package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassengerNotAvailableInfo implements Serializable {

    @JsonProperty("passengerType")
    private String passengerType;

    @JsonProperty("reason")
    private String reason;
}
