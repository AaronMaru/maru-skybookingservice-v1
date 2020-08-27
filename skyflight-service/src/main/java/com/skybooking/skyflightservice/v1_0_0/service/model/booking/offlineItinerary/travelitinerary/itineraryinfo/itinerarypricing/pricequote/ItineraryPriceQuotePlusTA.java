package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class ItineraryPriceQuotePlusTA implements Serializable {
    private String tourCode;
    private Boolean verifyFareCalc;
    private Boolean nucSuppresion;
    private String ticketingInstructionsInfo;
    private String itBtFare;
    private String domesticIntlInd;
    private Boolean manualFare;
    private Boolean subjToGovtApproval;
    private BigDecimal discountAmount;
    private String pricingStatus;
    private String systemIndicator;
    private Boolean negotiatedFare;
    private Boolean displayOnly;
    private Boolean itineraryChanged;
    private ItineraryPricePassengerInfoTA passengerInfo;
}