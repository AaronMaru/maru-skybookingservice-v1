package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itinerarypricing.pricequote.priceditinerary;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation.flightsegment.SegmentLocationTA;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PtcFlightSegmentTA implements Serializable {
    private Integer segmentNumber;
    private String status;
    private String departureDateTime;
    private String connectionInd;
    private Integer flightNumber;
    private String resBookDesigCode;
    private SegmentLocationTA originLocation;
    private ValidityDatesTA validityDates;
    private FareBasisTA fareBasis;
    private BaggageAllowanceTA baggageAllowance;
    private MarketingAirlineTA marketingAirline;
}
