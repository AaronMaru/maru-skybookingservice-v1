package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SegmentAssociation implements Serializable {
    @JsonProperty("AirSegment")
    private AirSegment airSegment;
    @JsonProperty("SegmentAssociationId")
    private int segmentAssociationId;
    @JsonProperty("Id")
    private int id;
}
