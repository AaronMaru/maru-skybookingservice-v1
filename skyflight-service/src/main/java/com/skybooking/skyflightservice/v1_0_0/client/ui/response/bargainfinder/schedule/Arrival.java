package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Arrival {

    @JsonProperty("airport")
    private String airport;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("state")
    private String state;

    @JsonProperty("terminal")
    private String terminal;

    @JsonProperty("time")
    private String time;

    @JsonProperty("dateAdjustment")
    private Integer dateAdjustment;
}
