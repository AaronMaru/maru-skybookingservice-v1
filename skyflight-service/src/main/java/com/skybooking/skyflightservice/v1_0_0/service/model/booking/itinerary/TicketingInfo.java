package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TicketingInfo implements Serializable {

    @JsonProperty("TariffBasis")
    private String tariffBasis;
    @JsonProperty("Exchange")
    private Exchange exchange;
    private ETicket eTicket;
    @JsonProperty("Ticketing")
    private Ticketing ticketing;

}
