package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ItineraryPricing implements Serializable {

    @JsonProperty("PriceQuoteTotals")
    private PriceQuoteTotals priceQuoteTotals;
    @JsonProperty("PriceQuote")
    private List<PriceQuote> priceQuote;

}
