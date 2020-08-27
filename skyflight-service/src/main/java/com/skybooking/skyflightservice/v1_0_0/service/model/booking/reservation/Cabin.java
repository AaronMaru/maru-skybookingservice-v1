package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Cabin implements Serializable {
    @JsonProperty("SabreCode")
    public String sabreCode;
    @JsonProperty("ShortName")
    public String shortName;
    @JsonProperty("Lang")
    public String lang;
    @JsonProperty("Code")
    public String code;
    @JsonProperty("Name")
    public String name;
}
