package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ItineraryInfo implements Serializable {

    @JsonProperty("ReservationItems")
    private ReservationItems reservationItems;
    @JsonProperty("ItineraryPricing")
    private ItineraryPricing itineraryPricing;
    @JsonProperty("Ticketing")
    private List<Ticketing> ticketing;

}
