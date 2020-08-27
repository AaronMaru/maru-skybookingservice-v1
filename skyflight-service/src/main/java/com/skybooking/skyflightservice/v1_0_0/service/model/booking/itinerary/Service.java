package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Service implements Serializable {
    @JsonProperty("SSR_Type")
    private String sSR_Type;
    @JsonProperty("Airline")
    private Airline airline;
    @JsonProperty("SSR_Code")
    private String sSR_Code;
    @JsonProperty("Text")
    private String text;
    @JsonProperty("PersonName")
    private PersonName personName;

}
