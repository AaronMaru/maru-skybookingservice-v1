package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Poc implements Serializable {
    @JsonProperty("Departure")
    private String departure;
    @JsonProperty("Airport")
    private String airport;
}
