package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.reservation;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OpenReservationElementTA implements Serializable {
    private String elementId;
    private String type;
    private Integer id;
    private ReservationNameAssociationTA nameAssociation;
    private ReservationServiceRequestTA serviceRequest;
    private ReservationSegmentAssociationTA segmentAssociation;
}