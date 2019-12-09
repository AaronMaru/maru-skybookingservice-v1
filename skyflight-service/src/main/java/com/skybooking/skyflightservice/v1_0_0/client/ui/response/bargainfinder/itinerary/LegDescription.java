package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LegDescription {

    @JsonProperty("arrivalLocation")
    private String arrivalLocation;

    @JsonProperty("departureDate")
    private String departureDate;

    @JsonProperty("departureLocation")
    private String departureLocation;
}
