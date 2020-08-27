package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Form implements Serializable {

    @JsonProperty("Text")
    private String text;
    @JsonProperty("RPH")
    private String rPH;
    @JsonProperty("Id")
    private int id;

}
