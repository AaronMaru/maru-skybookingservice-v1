package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SignatureLine implements Serializable {

    @JsonProperty("Status")
    private String status;
    @JsonProperty("ExpirationDateTime")
    private String expirationDateTime;
    @JsonProperty("Text")
    private String text;
    @JsonProperty("Source")
    private String source;

}
