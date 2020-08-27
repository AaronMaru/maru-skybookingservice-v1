package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AirItineraryPricingInfo implements Serializable {

    @JsonProperty("ItinTotalFare")
    private ItinTotalFare itinTotalFare;
    @JsonProperty("PTC_FareBreakdown")
    private PTCFareBreakdown pTC_FareBreakdown;
    @JsonProperty("TicketingFees")
    private String ticketingFees;
    @JsonProperty("SignatureLine")
    private SignatureLine signatureLine;

}
