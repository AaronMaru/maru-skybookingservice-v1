package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PassengerTypeQuantity implements Serializable {

    @JsonProperty("Quantity")
    private int quantity;
    @JsonProperty("Code")
    private String code;

}
