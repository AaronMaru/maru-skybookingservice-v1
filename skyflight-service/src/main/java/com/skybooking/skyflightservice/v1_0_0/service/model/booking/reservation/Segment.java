package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Segment implements Serializable {
    @JsonProperty("Air")
    private Air air;
    private int sequence;
    private int id;
}
