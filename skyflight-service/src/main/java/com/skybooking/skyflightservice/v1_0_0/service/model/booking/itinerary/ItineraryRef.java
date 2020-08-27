package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ItineraryRef implements Serializable {

    @JsonProperty("PrimeHostID")
    private String primeHostID;
    @JsonProperty("InhibitCode")
    private String inhibitCode;
    @JsonProperty("Source")
    private Source source;
    @JsonProperty("ID")
    private String iD;
    @JsonProperty("Header")
    private String header;
    @JsonProperty("AirExtras")
    private boolean airExtras;
    @JsonProperty("PartitionID")
    private String partitionID;

}
