package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BookingDetails implements Serializable {

    @JsonProperty("RecordLocator")
    private String recordLocator;
    @JsonProperty("SystemCreationTimestamp")
    private Date systemCreationTimestamp;
    @JsonProperty("CreationTimestamp")
    private Date creationTimestamp;
    @JsonProperty("DivideSplitDetails")
    private String divideSplitDetails;
    @JsonProperty("EstimatedPurgeTimestamp")
    private Date estimatedPurgeTimestamp;
    @JsonProperty("CreationAgentID")
    private String creationAgentID;
    @JsonProperty("UpdateTimestamp")
    private Date updateTimestamp;
    @JsonProperty("PNRSequence")
    private int pNRSequence;
    @JsonProperty("FlightsRange")
    private FlightsRange flightsRange;
    @JsonProperty("UpdateToken")
    private String updateToken;

}
