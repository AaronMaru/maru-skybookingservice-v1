package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Air implements Serializable {

    @JsonProperty("FunnelFlight")
    private boolean funnelFlight;
    @JsonProperty("MarketingClassOfService")
    private String marketingClassOfService;
    @JsonProperty("ClassOfService")
    private String classOfService;
    @JsonProperty("MarketingFlightNumber")
    private int marketingFlightNumber;
    @JsonProperty("Cabin")
    private Cabin cabin;
    @JsonProperty("FlightNumber")
    private int flightNumber;
    @JsonProperty("NumberInParty")
    private int numberInParty;
    @JsonProperty("AirMilesFlown")
    private int airMilesFlown;
    @JsonProperty("DepartureAirport")
    private String departureAirport;
    @JsonProperty("MarketingAirlineCode")
    private String marketingAirlineCode;
    @JsonProperty("SegmentBookedDate")
    private Date segmentBookedDate;
    @JsonProperty("ScheduleChangeIndicator")
    private boolean scheduleChangeIndicator;
    @JsonProperty("ChangeOfGauge")
    private boolean changeOfGauge;
    private boolean outboundConnection;
    @JsonProperty("Eticket")
    private boolean eticket;
    @JsonProperty("DepartureDateTime")
    private Date departureDateTime;
    private int segmentAssociationId;
    private int sequence;
    @JsonProperty("ArrivalDateTime")
    private Date arrivalDateTime;
    @JsonProperty("EquipmentType")
    private String equipmentType;
    @JsonProperty("ActionCode")
    private String actionCode;
    @JsonProperty("DisclosureCarrier")
    private DisclosureCarrier disclosureCarrier;
    @JsonProperty("AirlineRefId")
    private String airlineRefId;
    @JsonProperty("ElapsedTime")
    private int elapsedTime;
    @JsonProperty("ArrivalAirport")
    private String arrivalAirport;
    private boolean inboundConnection;


}
