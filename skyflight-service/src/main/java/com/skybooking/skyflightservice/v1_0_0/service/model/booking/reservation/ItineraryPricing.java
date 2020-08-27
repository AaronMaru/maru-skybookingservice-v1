package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ItineraryPricing implements Serializable {
    @JsonProperty("PricedItinerary")
    private List<PricedItinerary> pricedItinerary;
}
