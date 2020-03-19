package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItineraryODInfoRS {

    private Integer elapsedTime;
    private Boolean multiAirlineStatus;
    private String multiAirlineUrl;
    private Integer adjustmentDate;
    private Integer stop;

    List<ItineraryODSegmentRS> itinerarySegment = new ArrayList<>();

}
