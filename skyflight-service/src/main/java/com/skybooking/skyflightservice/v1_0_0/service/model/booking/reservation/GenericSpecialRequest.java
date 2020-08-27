package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GenericSpecialRequest implements Serializable {

    private int id;
    @JsonProperty("Code")
    private String code;
    private String msgType;
    @JsonProperty("AirlineCode")
    private String airlineCode;
    @JsonProperty("FullText")
    private String fullText;
    private String type;
    @JsonProperty("FreeText")
    private String freeText;
    @JsonProperty("NumberInParty")
    private int numberInParty;
    @JsonProperty("ActionCode")
    private String actionCode;
    @JsonProperty("TicketNumber")
    private Object ticketNumber;
}
