package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SpecialServiceInfo implements Serializable {

    @JsonProperty("Type")
    private String type;
    @JsonProperty("RPH")
    private String rPH;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Service")
    private Service service;

}
