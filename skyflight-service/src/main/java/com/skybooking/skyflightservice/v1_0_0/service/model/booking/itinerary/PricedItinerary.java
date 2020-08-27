package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PricedItinerary implements Serializable {

    @JsonProperty("TaxExempt")
    private boolean taxExempt;
    @JsonProperty("ValidatingCarrier")
    private String validatingCarrier;
    @JsonProperty("AirItineraryPricingInfo")
    private AirItineraryPricingInfo airItineraryPricingInfo;
    @JsonProperty("NetTicketingInfo")
    private NetTicketingInfo netTicketingInfo;
    @JsonProperty("DisplayOnly")
    private boolean displayOnly;
    @JsonProperty("RPH")
    private int rPH;
    @JsonProperty("InputMessage")
    private String inputMessage;
    @JsonProperty("StatusCode")
    private String statusCode;
    @JsonProperty("StoredDateTime")
    private String storedDateTime;

}
