package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Ticketing implements Serializable {

    @JsonProperty("ConjunctedCount")
    private int conjunctedCount;
    @JsonProperty("TicketTimeLimit")
    private String ticketTimeLimit;
    @JsonProperty("RPH")
    private String rPH;
    private String eTicketNumber;

}
