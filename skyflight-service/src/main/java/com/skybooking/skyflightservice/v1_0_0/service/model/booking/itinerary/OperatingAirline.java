package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OperatingAirline implements Serializable {

    @JsonProperty("FlightNumber")
    private String flightNumber;
    @JsonProperty("ResBookDesigCode")
    private String resBookDesigCode;
    @JsonProperty("Banner")
    private String banner;
    @JsonProperty("Code")
    private String code;

}