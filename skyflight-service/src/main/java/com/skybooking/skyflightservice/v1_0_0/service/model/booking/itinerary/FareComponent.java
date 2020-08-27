package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FareComponent implements Serializable {

    @JsonProperty("TicketDesignator")
    private String ticketDesignator;
    @JsonProperty("GoverningCarrier")
    private String governingCarrier;
    @JsonProperty("FlightSegmentNumbers")
    private FlightSegmentNumbers flightSegmentNumbers;
    @JsonProperty("FareBasisCode")
    private String fareBasisCode;
    @JsonProperty("Amount")
    private BigDecimal amount;
    @JsonProperty("FareDirectionality")
    private String fareDirectionality;
    @JsonProperty("FareComponentNumber")
    private int fareComponentNumber;
    @JsonProperty("Dates")
    private Dates dates;
    @JsonProperty("Location")
    private Location location;

}
