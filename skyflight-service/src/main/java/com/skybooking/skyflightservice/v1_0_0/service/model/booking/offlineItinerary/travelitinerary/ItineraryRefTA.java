package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryRef.ItineraryRefSource;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItineraryRefTA implements Serializable {
    private ItineraryRefSource source;
    private String primeHostId;
    private String inhibitCode;
    private String id;
    private String header;
    private Boolean airExtras;
    private String partitionId;
}
