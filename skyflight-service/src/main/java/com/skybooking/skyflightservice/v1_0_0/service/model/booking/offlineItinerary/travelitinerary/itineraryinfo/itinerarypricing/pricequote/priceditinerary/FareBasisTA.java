package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.priceditinerary;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class FareBasisTA implements Serializable {
    private String code;
}
