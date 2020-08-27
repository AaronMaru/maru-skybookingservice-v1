package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.reservation;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReservationSegmentAssociationTA implements Serializable {
    private Integer id;
    private Integer segmentAssociationId;
    private ReservationAirSegment airSegment;
}
