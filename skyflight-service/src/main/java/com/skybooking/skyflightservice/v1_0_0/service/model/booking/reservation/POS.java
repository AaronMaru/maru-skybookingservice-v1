package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class POS implements Serializable {
    @JsonProperty("InhibitCode")
    private String inhibitCode;
    @JsonProperty("Source")
    private Source source;
    @JsonProperty("AirExtras")
    private boolean airExtras;
}
