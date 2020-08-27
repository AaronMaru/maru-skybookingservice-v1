package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SegmentAssociation implements Serializable {
    @JsonProperty("SegmentAssociationId")
    private int segmentAssociationId;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("AirSegment")
    private AirSegment airSegment;
}
