package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ItineraryPricePassengerInfoTA implements Serializable {
    private String passengerType;
    private List<ItineraryPricePassengerDataTA> passengerData;
}
