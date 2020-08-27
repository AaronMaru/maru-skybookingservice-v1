package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PTCFareBreakdown implements Serializable {

    @JsonProperty("FareCalc")
    private String fareCalc;
    @JsonProperty("FareComponent")
    private List<FareComponent> fareComponent;
    @JsonProperty("PassengerTypePriced")
    private PassengerTypePriced passengerTypePriced;
    @JsonProperty("FlightSegment")
    private List<FlightSegment> flightSegment;
    @JsonProperty("PassengerTypeQuantity")
    private PassengerTypeQuantity passengerTypeQuantity;
    @JsonProperty("FareBasisCode")
    private String fareBasisCode;
    @JsonProperty("PassengerTypeRequested")
    private PassengerTypeRequested passengerTypeRequested;

}
