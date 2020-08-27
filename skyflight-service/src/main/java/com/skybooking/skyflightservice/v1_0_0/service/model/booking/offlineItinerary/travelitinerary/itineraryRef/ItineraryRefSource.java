package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryRef;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItineraryRefSource implements Serializable {

    private String homePseudoCityCode;
    private String lastUpdateDateTime;
    private String receivedFrom;
    private String createDateTime;
    private Integer sequenceNumber;
    private String aaaPseudoCityCode;
    private String creationAgent;
    private String pseudoCityCode;

}
