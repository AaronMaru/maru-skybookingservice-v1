package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.specialserviceinfo.SpecialServiceInfoServiceTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SpecialServiceInfoTA implements Serializable {
    private String type;
    private String rph;
    private Integer id;
    private SpecialServiceInfoServiceTA service;
}
