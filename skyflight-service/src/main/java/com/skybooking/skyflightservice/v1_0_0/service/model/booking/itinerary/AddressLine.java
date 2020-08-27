package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressLine implements Serializable {

    @JsonProperty("Id")
    private int id;
    private String type;
    private String content;

}
