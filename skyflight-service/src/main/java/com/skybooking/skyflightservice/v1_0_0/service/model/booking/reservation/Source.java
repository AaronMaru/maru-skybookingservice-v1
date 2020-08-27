package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Source implements Serializable {
    @JsonProperty("BookingSource")
    private String bookingSource;
    @JsonProperty("PrimeHostID")
    private String primeHostID;
    @JsonProperty("HomePseudoCityCode")
    private String homePseudoCityCode;
    @JsonProperty("AgentDutyCode")
    private String agentDutyCode;
    @JsonProperty("AirlineVendorID")
    private String airlineVendorID;
    @JsonProperty("PseudoCityCode")
    private String pseudoCityCode;
    @JsonProperty("AgentSine")
    private String agentSine;
    @JsonProperty("ISOCountry")
    private String iSOCountry;
}
