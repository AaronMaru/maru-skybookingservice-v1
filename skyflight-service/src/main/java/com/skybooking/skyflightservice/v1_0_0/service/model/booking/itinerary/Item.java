package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Item implements Serializable {

    @JsonProperty("FlightSegment")
    private List<FlightSegment> flightSegment;
    @JsonProperty("RPH")
    private int rPH;
    @JsonProperty("Product")
    private Product product;

}
