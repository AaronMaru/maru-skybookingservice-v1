package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation.flightsegment;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SegmentEquipmentTA implements Serializable {
    private String airEquipType;
}
