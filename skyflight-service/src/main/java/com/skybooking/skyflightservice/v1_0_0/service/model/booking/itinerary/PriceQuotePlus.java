package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PriceQuotePlus implements Serializable {
    @JsonProperty("TourCode")
    private String tourCode;
    @JsonProperty("VerifyFareCalc")
    private boolean verifyFareCalc;
    @JsonProperty("NUCSuppresion")
    private boolean nUCSuppresion;
    @JsonProperty("TicketingInstructionsInfo")
    private String ticketingInstructionsInfo;
    @JsonProperty("IT_BT_Fare")
    private String iT_BT_Fare;
    @JsonProperty("DomesticIntlInd")
    private String domesticIntlInd;
    @JsonProperty("ManualFare")
    private boolean manualFare;
    @JsonProperty("SubjToGovtApproval")
    private boolean subjToGovtApproval;
    @JsonProperty("DiscountAmount")
    private BigDecimal discountAmount;
    @JsonProperty("PricingStatus")
    private String pricingStatus;
    @JsonProperty("SystemIndicator")
    private String systemIndicator;
    @JsonProperty("NegotiatedFare")
    private boolean negotiatedFare;
    @JsonProperty("PassengerInfo")
    private PassengerInfo passengerInfo;
    @JsonProperty("DisplayOnly")
    private boolean displayOnly;
    @JsonProperty("ItineraryChanged")
    private boolean itineraryChanged;
}
