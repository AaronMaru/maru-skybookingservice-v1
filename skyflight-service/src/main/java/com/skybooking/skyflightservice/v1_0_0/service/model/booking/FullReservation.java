package com.skybooking.skyflightservice.v1_0_0.service.model.booking;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary.Itinerary;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.OfflineItineraryTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation.Reservation;
import lombok.Data;

import java.io.Serializable;

@Data
public class FullReservation implements Serializable {
    private Reservation reservation;
    private Itinerary itineraryDetail;
}
