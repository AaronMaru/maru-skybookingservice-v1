package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonName implements Serializable {

    private String elementId;
    @JsonProperty("Surname")
    private String surname;
    @JsonProperty("PassengerType")
    private String passengerType;
    @JsonProperty("NameNumber")
    private double nameNumber;
    @JsonProperty("WithInfant")
    private boolean withInfant;
    @JsonProperty("RPH")
    private int rPH;
    @JsonProperty("GivenName")
    private String givenName;
    @JsonProperty("NameReference")
    private String nameReference;
    private String content;

}
