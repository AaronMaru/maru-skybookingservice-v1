package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.ItineraryPriceQuoteTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.ItineraryPriceQuoteTotalTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ItineraryPricingTA implements Serializable {
    private ItineraryPriceQuoteTotalTA priceQuoteTotal;
    private List<ItineraryPriceQuoteTA> priceQuote;
}
