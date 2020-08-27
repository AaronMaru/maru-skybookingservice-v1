package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChildRequest implements Serializable {
    @JsonProperty("DateOfBirth")
    private String dateOfBirth;
    @JsonProperty("NumberInParty")
    private int numberInParty;
    private int id;
    @JsonProperty("ActionCode")
    private String actionCode;
    private String type;
    @JsonProperty("VendorCode")
    private String vendorCode;
}
