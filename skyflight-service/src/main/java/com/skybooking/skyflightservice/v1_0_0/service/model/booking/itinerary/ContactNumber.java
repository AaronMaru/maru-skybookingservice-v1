package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ContactNumber implements Serializable {

    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("LocationCode")
    private String locationCode;
    @JsonProperty("RPH")
    private String rPH;
    @JsonProperty("Id")
    private int id;

}
