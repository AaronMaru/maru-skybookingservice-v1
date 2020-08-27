package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PassengerReservation implements Serializable {

    @JsonProperty("TicketingInfo")
    private TicketingInfo ticketingInfo;
    @JsonProperty("ItineraryPricing")
    private ItineraryPricing itineraryPricing;
    @JsonProperty("Passengers")
    private Passengers passengers;
    @JsonProperty("Segments")
    private Segments segments;

}
