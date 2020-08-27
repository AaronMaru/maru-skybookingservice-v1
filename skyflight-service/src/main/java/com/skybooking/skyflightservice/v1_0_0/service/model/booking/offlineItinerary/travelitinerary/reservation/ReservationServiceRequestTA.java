package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.reservation;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReservationServiceRequestTA implements Serializable {
    private String serviceType;
    private String code;
    private Integer serviceCount;
    private String ssrType;
    private String airlineCode;
    private String fullText;
    private String actionCode;
    private String freeText;
}
