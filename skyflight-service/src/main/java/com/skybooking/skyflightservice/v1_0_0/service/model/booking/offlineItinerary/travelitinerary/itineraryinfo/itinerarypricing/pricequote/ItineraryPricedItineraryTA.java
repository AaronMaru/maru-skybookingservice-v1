package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.priceditinerary.AirItineraryPricingInfoTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItineraryPricedItineraryTA implements Serializable {
    private Boolean taxExempt;
    private String validatingCarrier;
    private Boolean displayOnly;
    private Integer rph;
    private String inputMessage;
    private String statusCode;
    private String storedDateTime;
    private AirItineraryPricingInfoTA airItineraryPricingInfo;
}