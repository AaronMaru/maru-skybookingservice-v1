package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PricedItinerary implements Serializable {

    @JsonProperty("TaxExempt")
    private String taxExempt;
    @JsonProperty("AirItineraryPricingInfo")
    private AirItineraryPricingInfo airItineraryPricingInfo;
    @JsonProperty("ValidatingCarrier")
    private String validatingCarrier;
    @JsonProperty("SequenceNumber")
    private int sequenceNumber;
    @JsonProperty("NetTicketingInfo")
    private NetTicketingInfo netTicketingInfo;
    @JsonProperty("InputMessage")
    private String inputMessage;
    @JsonProperty("StatusCode")
    private String statusCode;

}
