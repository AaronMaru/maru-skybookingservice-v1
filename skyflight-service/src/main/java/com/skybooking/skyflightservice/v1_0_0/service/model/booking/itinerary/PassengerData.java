package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PassengerData implements Serializable {

    @JsonProperty("NameNumber")
    private double nameNumber;
    private String content;

}
