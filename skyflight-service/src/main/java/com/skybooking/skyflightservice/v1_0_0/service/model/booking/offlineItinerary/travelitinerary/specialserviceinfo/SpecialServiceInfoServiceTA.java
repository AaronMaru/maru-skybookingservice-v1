package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.specialserviceinfo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SpecialServiceInfoServiceTA implements Serializable {
    private String ssrCode;
    private String ssrType;
    private String text;
    private SpecialServiceInfoPersonNameTA personName;
    private SpecialServiceInfoAirlineTA airline;
}
