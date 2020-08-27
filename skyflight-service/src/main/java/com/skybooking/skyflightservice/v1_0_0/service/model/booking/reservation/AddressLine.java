package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddressLine implements Serializable {
    @JsonProperty("Text")
    private Object text;
    private int id;
    private String type;
}
