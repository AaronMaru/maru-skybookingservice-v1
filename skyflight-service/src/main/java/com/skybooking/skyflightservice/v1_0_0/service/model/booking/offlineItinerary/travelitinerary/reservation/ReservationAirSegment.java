package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.reservation;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReservationAirSegment implements Serializable {
    private String flightNumber;
    private String carrierCode;
    private String departureDate;
    private String boardPoint;
    private String classOfService;
    private String offPoint;
}