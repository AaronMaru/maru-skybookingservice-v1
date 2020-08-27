package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DisclosureCarrier implements Serializable {

    @JsonProperty("DOT")
    public boolean dOT;
    @JsonProperty("Banner")
    public String banner;
    @JsonProperty("Code")
    public String code;

}
