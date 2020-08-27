package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItineraryTicketingTA implements Serializable {
    private String ticketTimeLimit;
    private Object rph;
    private String eTicketNumber;
}