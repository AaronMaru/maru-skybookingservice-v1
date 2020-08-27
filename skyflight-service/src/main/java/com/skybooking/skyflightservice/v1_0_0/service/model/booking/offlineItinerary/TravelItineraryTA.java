package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TravelItineraryTA implements Serializable {
    private CustomerInfoTA customerInfo;
    private List<AccountingInfoTA> accountingInfo;
    private RemarkInfoTA remarkInfo;
    private ItineraryInfoTA itineraryInfo;
    private OpenReservationElementsTA openReservationElements;
    private ItineraryRefTA itineraryRef;
    private List<SpecialServiceInfoTA> specialServiceInfo;
}
