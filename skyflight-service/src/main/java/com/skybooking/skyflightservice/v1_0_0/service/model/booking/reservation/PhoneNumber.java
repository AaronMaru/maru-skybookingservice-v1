package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PhoneNumber implements Serializable {

    private String elementId;
    @JsonProperty("CityCode")
    private String cityCode;
    private int index;
    private int id;
    @JsonProperty("Number")
    private String number;

}
