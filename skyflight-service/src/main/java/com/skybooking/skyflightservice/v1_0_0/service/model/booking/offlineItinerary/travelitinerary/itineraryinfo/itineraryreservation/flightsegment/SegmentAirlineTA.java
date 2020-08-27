package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation.flightsegment;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SegmentAirlineTA implements Serializable {
    private String flightNumber;
    private String resBookDesigCode;
    private String banner;
    private String code;
}
