package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FlightSegment implements Serializable {
    @JsonProperty("ResBookDesigCode")
    private String resBookDesigCode;
    @JsonProperty("AirPort")
    private AirPort airPort;
    @JsonProperty("OperatingAirline")
    private String operatingAirline;
    @JsonProperty("DepartureDateTime")
    private String departureDateTime;
    @JsonProperty("RPH")
    private String rPH;
    @JsonProperty("ActionCode")
    private String actionCode;
    @JsonProperty("FlightType")
    private String flightType;
    @JsonProperty("FareBasisCode")
    private String fareBasisCode;
    @JsonProperty("FlightNumber")
    private int flightNumber;
    @JsonProperty("ValidityDates")
    private ValidityDates validityDates;
}
