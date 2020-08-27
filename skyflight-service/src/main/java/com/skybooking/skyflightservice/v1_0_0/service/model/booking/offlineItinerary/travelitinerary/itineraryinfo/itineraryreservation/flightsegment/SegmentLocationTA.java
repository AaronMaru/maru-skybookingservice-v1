package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation.flightsegment;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SegmentLocationTA implements Serializable {
    private Integer terminalCode;
    private String locationCode;
    private String terminal;
}
