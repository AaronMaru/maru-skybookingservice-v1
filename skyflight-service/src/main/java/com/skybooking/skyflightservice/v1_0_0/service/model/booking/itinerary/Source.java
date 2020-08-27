package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Source implements Serializable {

    @JsonProperty("HomePseudoCityCode")
    private String homePseudoCityCode;
    @JsonProperty("LastUpdateDateTime")
    private String lastUpdateDateTime;
    @JsonProperty("ReceivedFrom")
    private String receivedFrom;
    @JsonProperty("CreateDateTime")
    private String createDateTime;
    @JsonProperty("SequenceNumber")
    private int sequenceNumber;
    @JsonProperty("AAA_PseudoCityCode")
    private String aAA_PseudoCityCode;
    @JsonProperty("CreationAgent")
    private String creationAgent;
    @JsonProperty("PseudoCityCode")
    private String pseudoCityCode;

}
