package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FareComponent implements Serializable {
    @JsonProperty("FlightSegmentNumbers")
    private FlightSegmentNumbers flightSegmentNumbers;
    @JsonProperty("TicketDesignator")
    private String ticketDesignator;
    @JsonProperty("GoverningCarrier")
    private String governingCarrier;
    @JsonProperty("Location")
    private Location location;
    @JsonProperty("FareBasisCode")
    private String fareBasisCode;
    @JsonProperty("Amount")
    private int amount;
    @JsonProperty("FareDirectionality")
    private String fareDirectionality;
    @JsonProperty("FareComponentNumber")
    private int fareComponentNumber;
    @JsonProperty("Dates")
    private Dates dates;
}
