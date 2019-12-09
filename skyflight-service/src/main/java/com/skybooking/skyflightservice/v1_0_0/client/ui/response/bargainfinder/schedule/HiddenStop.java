package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HiddenStop {

    @JsonProperty("airport")
    private String airport;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;

    @JsonProperty("airMiles")
    private Integer airMiles;

    @JsonProperty("elapsedTime")
    private Integer elapsedTime;

    @JsonProperty("equipment")
    private String equipment;

    @JsonProperty("arrivalTime")
    private String arrivalTime;

    @JsonProperty("arrivalDateAdjustment")
    private Integer arrivalDateAdjustment;

    @JsonProperty("departureTime")
    private String departureTime;

    @JsonProperty("departureDateAdjustment")
    private Integer departureDateAdjustment;

    @JsonProperty("elapsedLayoverTime")
    private Integer elapsedLayoverTime;

}
