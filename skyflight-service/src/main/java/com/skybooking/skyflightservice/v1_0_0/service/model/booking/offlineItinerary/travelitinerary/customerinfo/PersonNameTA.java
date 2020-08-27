package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.customerinfo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PersonNameTA implements Serializable {

    private String elementId;
    private String surname;
    private String givenName;
    private String passengerType;
    private Double nameNumber;
    private Boolean withInfant;
    private Integer rph;

}
