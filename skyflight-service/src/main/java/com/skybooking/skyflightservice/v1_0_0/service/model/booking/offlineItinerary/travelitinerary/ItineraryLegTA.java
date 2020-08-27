package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation.ReservationProductDetailAirTA;
import lombok.Data;

import java.util.List;

@Data
public class ItineraryLegTA {

    private Integer elapsedTime;
    private boolean multipleAirline = false;
    List<ReservationProductDetailAirTA> segments;
}
