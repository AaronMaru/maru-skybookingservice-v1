package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.itineraryinfo.itineraryreservation;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ReservationProductDetailAirTA implements Serializable {
    private Boolean funnelFlight;
    private String marketingClassOfService;
    private String mealCode;
    private String arrivalTerminalName;
    private String classOfService;
    private Integer marketingFlightNumber;
    private Integer flightNumber;
    private Integer numberInParty;
    private Integer airMilesFlown;
    private String departureAirport;
    private String marketingAirlineCode;
    private String segmentBookedDate;
    private Integer arrivalTerminalCode;
    private Boolean scheduleChangeIndicator;
    private Boolean changeOfGauge;
    private Boolean outboundConnection;
    private Boolean eTicket;
    private String departureDateTime;
    private Integer segmentAssociationId;
    private Integer sequence;
    private String arrivalDateTime;
    private String equipmentType;
    private String actionCode;
    private String airlineRefId;
    private Integer elapsedTime;
    private String arrivalAirport;
    private Boolean inboundConnection;
    private ReservationProductAirCabinTA cabin;
    private ReservationDisclosureCarrierTA disclosureCarrier;
}
