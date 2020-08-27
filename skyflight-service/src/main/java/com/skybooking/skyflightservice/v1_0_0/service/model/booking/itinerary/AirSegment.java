package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AirSegment implements Serializable {


    @JsonProperty("FlightNumber")
    private String flightNumber;
    @JsonProperty("CarrierCode")
    private String carrierCode;
    @JsonProperty("DepartureDate")
    private String departureDate;
    @JsonProperty("BoardPoint")
    private String boardPoint;
    @JsonProperty("ClassOfService")
    private String classOfService;
    @JsonProperty("OffPoint")
    private String offPoint;

}
