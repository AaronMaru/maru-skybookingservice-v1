package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation;

import com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation.flightsegment.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReservationFlightSegmentTA implements Serializable {
    private SegmentAirlineTA marketingAirline;
    private SegmentAirlineTA operatingAirline;
    private SegmentAirlineTA operatingAirlinePricing;
    private SegmentLocationTA originLocation;
    private SegmentLocationTA destinationLocation;
    private ReservationDisclosureCarrierTA disclosureCarrier;
    private SegmentMealTA meal;
    private SegmentSupplierRefTA supplierRef;
    private SegmentEquipmentTA equipment;
    private ReservationProductAirCabinTA cabin;
    private Boolean codeShare;
    private String numberInParty;
    private String stopQuantity;
    private Double elapsedTime;
    private String resBookDesigCode;
    private String segmentNumber;
    private String arrivalDateTime;
    private String updatedArrivalTime;
    private Boolean specialMeal;
    private String updatedDepartureTime;
    private String status;
    private Integer dayOfWeekInd;
    private Boolean smokingAllowed;
    private String airMilesFlown;
    private Boolean isPast;
    private Boolean eTicket;
    private String departureDateTime;
    private String segmentBookedDate;
    private String flightNumber;
    private Integer id;
}
