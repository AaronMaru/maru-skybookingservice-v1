package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Cabin implements Serializable {

    @JsonProperty("SabreCode")
    private String sabreCode;
    @JsonProperty("ShortName")
    private String shortName;
    @JsonProperty("Lang")
    private String lang;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Name")
    private String name;

}
