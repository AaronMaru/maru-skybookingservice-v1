package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PrivateFareInformation implements Serializable {

    @JsonProperty("PrivateFareType")
    private String privateFareType;
    @JsonProperty("Text")
    private String text;

}
