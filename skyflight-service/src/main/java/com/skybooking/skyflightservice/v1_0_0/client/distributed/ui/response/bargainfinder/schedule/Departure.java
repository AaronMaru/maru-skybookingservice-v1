package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Departure implements Serializable {

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

}
