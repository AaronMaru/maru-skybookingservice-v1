package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AccountingInfo implements Serializable {

    @JsonProperty("BaseFare")
    private BaseFare baseFare;
    @JsonProperty("FareApplication")
    private String fareApplication;
    @JsonProperty("PersonName")
    private PersonName personName;
    @JsonProperty("Airline")
    private Airline airline;
    @JsonProperty("TicketingInfo")
    private TicketingInfo ticketingInfo;
    @JsonProperty("PaymentInfo")
    private AccountingPaymentInfo paymentInfo;
    @JsonProperty("DocumentInfo")
    private DocumentInfo documentInfo;
    @JsonProperty("Taxes")
    private Taxes taxes;
    @JsonProperty("Id")
    private int id;

}
