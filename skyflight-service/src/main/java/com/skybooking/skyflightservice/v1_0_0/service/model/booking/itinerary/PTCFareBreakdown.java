package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PTCFareBreakdown implements Serializable {

    @JsonProperty("FareComponent")
    private List<FareComponent> fareComponent;
    @JsonProperty("FareCalculation")
    private FareCalculation fareCalculation;
    @JsonProperty("FareSource")
    private String fareSource;
    @JsonProperty("FareBasis")
    private FareBasis fareBasis;
    @JsonProperty("FlightSegment")
    private List<FlightSegment> flightSegment;
    @JsonProperty("Endorsements")
    private Endorsements endorsements;
    @JsonProperty("TourCode")
    private TourCode tourCode;

}
