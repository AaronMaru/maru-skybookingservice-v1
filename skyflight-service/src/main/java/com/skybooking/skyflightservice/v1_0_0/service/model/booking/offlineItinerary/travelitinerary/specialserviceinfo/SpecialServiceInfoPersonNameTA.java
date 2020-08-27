package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.specialserviceinfo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SpecialServiceInfoPersonNameTA implements Serializable {
    private String nameNumber;
    private String content;
}
