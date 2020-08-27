package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class NameAssociation implements Serializable {
    @JsonProperty("NameRefNumber")
    private double nameRefNumber;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("ReferenceId")
    private int referenceId;
    @JsonProperty("LastName")
    private String lastName;
}
