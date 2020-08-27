package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Passenger implements Serializable {

    private String nameType;
    private String elementId;
    private String passengerType;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("SpecialRequests")
    private SpecialRequests specialRequests;
    private int nameAssocId;
    private double nameId;
    private boolean withInfant;
    private int id;
    @JsonProperty("Seats")
    private String seats;
    private String referenceNumber;

}
