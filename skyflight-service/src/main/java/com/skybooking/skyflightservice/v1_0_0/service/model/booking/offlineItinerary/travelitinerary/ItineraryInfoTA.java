package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.ItineraryPricingTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.ItineraryReservationItemsTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.ItineraryTicketingTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ItineraryInfoTA implements Serializable {
    private ItineraryReservationItemsTA reservationItem;
    private ItineraryPricingTA itineraryPricing;
    private List<ItineraryTicketingTA> ticketing;
}
