package com.skybooking.backofficeservice.v1_0_0.ui.model.response.flight.flightDetail;

import com.skybooking.backofficeservice.v1_0_0.client.model.response.flight.detail.ItinerarySegment;
import lombok.Data;

import java.util.List;

@Data
public class ItineraryInfo {
    private Integer stop;
    private String adjustmentDate;
    private List<ItinerarySegment> itinerarySegment;
}
