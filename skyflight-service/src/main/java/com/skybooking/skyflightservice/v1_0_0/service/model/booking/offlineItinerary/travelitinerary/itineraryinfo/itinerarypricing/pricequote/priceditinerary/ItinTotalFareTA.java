package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.priceditinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.ItineraryPriceQuoteTotalTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.price.ItineraryPriceModelTA;
import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.tax.ItineraryTaxesTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItinTotalFareTA implements Serializable {
    private ItineraryPriceModelTA baseFare;
    private ItineraryPriceModelTA totalFare;
    private ItineraryPriceModelTA equivFare;
    private ItineraryTaxesTA taxes;
    private ItineraryPriceQuoteTotalTA totals;
}
