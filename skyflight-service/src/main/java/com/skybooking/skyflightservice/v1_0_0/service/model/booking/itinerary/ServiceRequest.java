package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceRequest implements Serializable {

    private String serviceType;
    private String code;
    private String ssrType;
    private String airlineCode;
    @JsonProperty("FullText")
    private String fullText;
    @JsonProperty("FreeText")
    private String freeText;
    private int serviceCount;
    private String actionCode;

}
