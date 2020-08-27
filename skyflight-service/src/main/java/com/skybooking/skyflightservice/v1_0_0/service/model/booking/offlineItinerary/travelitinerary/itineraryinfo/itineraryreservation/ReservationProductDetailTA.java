package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReservationProductDetailTA implements Serializable {
    private String productCategory;
    private ReservationProductNameTA productName;
    private ReservationProductDetailAirTA air;
}
