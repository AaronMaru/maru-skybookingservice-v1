package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PassengerInfo implements Serializable {

    @JsonProperty("PassengerType")
    private String passengerType;
    @JsonProperty("PassengerData")
    private List<PassengerData> passengerData;

}
