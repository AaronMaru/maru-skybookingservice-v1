package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AlreadyTicketed implements Serializable {

    private String elementId;
    @JsonProperty("Code")
    private String code;
    private int index;
    private int id;
}
