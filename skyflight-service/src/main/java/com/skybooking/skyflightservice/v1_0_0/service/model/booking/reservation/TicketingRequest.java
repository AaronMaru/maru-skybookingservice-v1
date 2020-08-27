package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TicketingRequest implements Serializable {

    @JsonProperty("ValidatingCarrier")
    private String validatingCarrier;
    @JsonProperty("NumberInParty")
    private int numberInParty;
    @JsonProperty("TicketNumber")
    private String ticketNumber;
    @JsonProperty("ClassOfService")
    private String classOfService;
    @JsonProperty("DateOfTravel")
    private Date dateOfTravel;
    @JsonProperty("TicketType")
    private String ticketType;
    @JsonProperty("ActionCode")
    private String actionCode;
    @JsonProperty("BoardPoint")
    private String boardPoint;
    @JsonProperty("OffPoint")
    private String offPoint;

}
