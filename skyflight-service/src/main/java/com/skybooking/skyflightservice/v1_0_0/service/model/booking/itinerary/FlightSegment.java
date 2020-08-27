package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FlightSegment implements Serializable {

    @JsonProperty("MarketingAirline")
    private MarketingAirline marketingAirline;
    @JsonProperty("CodeShare")
    private boolean codeShare;
    @JsonProperty("DisclosureCarrier")
    private DisclosureCarrier disclosureCarrier;
    @JsonProperty("NumberInParty")
    private String numberInParty;
    @JsonProperty("StopQuantity")
    private String stopQuantity;
    @JsonProperty("ElapsedTime")
    private double elapsedTime;
    @JsonProperty("ResBookDesigCode")
    private String resBookDesigCode;
    @JsonProperty("OperatingAirline")
    private OperatingAirline operatingAirline;
    @JsonProperty("SegmentNumber")
    private String segmentNumber;
    @JsonProperty("SupplierRef")
    private SupplierRef supplierRef;
    @JsonProperty("ArrivalDateTime")
    private String arrivalDateTime;
    @JsonProperty("UpdatedArrivalTime")
    private String updatedArrivalTime;
    @JsonProperty("SpecialMeal")
    private boolean specialMeal;
    @JsonProperty("Cabin")
    private Cabin cabin;
    @JsonProperty("Equipment")
    private Equipment equipment;
    @JsonProperty("UpdatedDepartureTime")
    private String updatedDepartureTime;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("DayOfWeekInd")
    private int dayOfWeekInd;
    @JsonProperty("SmokingAllowed")
    private boolean smokingAllowed;
    @JsonProperty("AirMilesFlown")
    private String airMilesFlown;
    @JsonProperty("OriginLocation")
    private OriginLocation originLocation;
    @JsonProperty("IsPast")
    private boolean isPast;
    private boolean eTicket;
    @JsonProperty("DepartureDateTime")
    private String departureDateTime;
    @JsonProperty("DestinationLocation")
    private DestinationLocation destinationLocation;
    @JsonProperty("SegmentBookedDate")
    private Date segmentBookedDate;
    @JsonProperty("FlightNumber")
    private String flightNumber;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("OperatingAirlinePricing")
    private OperatingAirlinePricing operatingAirlinePricing;
    @JsonProperty("ConnectionInd")
    private String connectionInd;
    @JsonProperty("BaggageAllowance")
    private BaggageAllowance baggageAllowance;
    @JsonProperty("FareBasis")
    private FareBasis fareBasis;
    @JsonProperty("ValidityDates")
    private ValidityDates validityDates;

}
