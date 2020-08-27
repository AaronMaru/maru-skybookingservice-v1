package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItineraryPriceSignatureLineTA implements Serializable {
    private String status;
    private String expirationDateTime;
    private String text;
    private String source;
}