package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReservationProductAirCabinTA implements Serializable {
    private String code;
    private String name;
    private String shortName;
    private String lang;
    private String sabreCode;
}
