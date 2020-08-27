package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AirSegment implements Serializable {

    @JsonProperty("OffPoint")
    private String offPoint;
    @JsonProperty("CarrierCode")
    private String carrierCode;
    @JsonProperty("BoardPoint")
    private String boardPoint;
    @JsonProperty("DepartureDate")
    private String departureDate;
    @JsonProperty("FlightNumber")
    private String flightNumber;
    @JsonProperty("ClassOfService")
    private String classOfService;

}
