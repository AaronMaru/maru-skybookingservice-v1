package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TicketDetail implements Serializable {
    private String elementId;
    @JsonProperty("TransactionIndicator")
    private String transactionIndicator;
    @JsonProperty("AgencyLocation")
    private String agencyLocation;
    @JsonProperty("DutyCode")
    private String dutyCode;
    @JsonProperty("TicketNumber")
    private String ticketNumber;
    @JsonProperty("PaymentType")
    private String paymentType;
    private int index;
    @JsonProperty("OriginalTicketDetails")
    private String originalTicketDetails;
    @JsonProperty("PassengerName")
    private String passengerName;
    private int id;
    @JsonProperty("Timestamp")
    private Date timestamp;
    @JsonProperty("AgentSine")
    private String agentSine;
}
