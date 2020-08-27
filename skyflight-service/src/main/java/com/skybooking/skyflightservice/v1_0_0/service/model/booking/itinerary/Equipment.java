package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Equipment implements Serializable {

    @JsonProperty("AirEquipType")
    private String airEquipType;

}
