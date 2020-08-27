package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.ItineraryPriceMiscInfoTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.ItineraryPriceQuotePlusTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.ItineraryPriceResponseHeaderTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.ItineraryPricedItineraryTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItineraryPriceQuoteTA implements Serializable {
    private String rph;
    private ItineraryPriceResponseHeaderTA responseHeader;
    private ItineraryPricedItineraryTA pricedItinerary;
    private ItineraryPriceMiscInfoTA miscInfo;
    private ItineraryPriceQuotePlusTA priceQuotePlus;
}
