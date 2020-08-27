package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PriceQuote implements Serializable {

    @JsonProperty("ResponseHeader")
    private ResponseHeader responseHeader;
    @JsonProperty("PricedItinerary")
    private PricedItinerary pricedItinerary;
    @JsonProperty("RPH")
    private int rPH;
    @JsonProperty("MiscInformation")
    private MiscInformation miscInformation;
    @JsonProperty("PriceQuotePlus")
    private PriceQuotePlus priceQuotePlus;

}
