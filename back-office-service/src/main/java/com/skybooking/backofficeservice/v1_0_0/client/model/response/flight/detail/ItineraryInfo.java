package com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.detail;

import lombok.Data;

import java.util.List;

@Data
public class ItineraryInfo {
    private Integer elapsedTime;
    private Boolean multiAirlineStatus;
    private String multiAirlineUrl;
    private Integer stop;
    private String adjustmentDate;
    private List<ItinerarySegment> itinerarySegment;
}
