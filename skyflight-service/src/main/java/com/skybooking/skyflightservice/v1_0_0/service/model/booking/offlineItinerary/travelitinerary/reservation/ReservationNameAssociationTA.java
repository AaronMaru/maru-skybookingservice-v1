package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.reservation;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReservationNameAssociationTA implements Serializable {
    private String firstName;
    private String lastName;
    private Integer referenceId;
    private Double nameRefNumber;
}
