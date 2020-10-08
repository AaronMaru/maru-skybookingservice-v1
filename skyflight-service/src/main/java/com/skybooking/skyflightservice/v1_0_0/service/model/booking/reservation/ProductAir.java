package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ProductAir implements Serializable {
    @JsonProperty("ArrivalTerminalCode")
    public String arrivalTerminalCode;
    @JsonProperty("OperatingAirlineCode")
    public String operatingAirlineCode;
    @JsonProperty("EquipmentType")
    public String equipmentType;
    @JsonProperty("AirlineRefId")
    public String airlineRefId;
    @JsonProperty("FunnelFlight")
    public boolean funnelFlight;
    @JsonProperty("NumberInParty")
    public int numberInParty;
    @JsonProperty("Eticket")
    public boolean eticket;
    @JsonProperty("SegmentBookedDate")
    public Date segmentBookedDate;
    @JsonProperty("Cabin")
    public Cabin cabin;
    @JsonProperty("ElapsedTime")
    public int elapsedTime;
    @JsonProperty("FlightNumber")
    public int flightNumber;
    public boolean inboundConnection;
    @JsonProperty("MarketingAirlineCode")
    public String marketingAirlineCode;
    public boolean outboundConnection;
    @JsonProperty("AirMilesFlown")
    public int airMilesFlown;
    @JsonProperty("DepartureDateTime")
    public Date departureDateTime;
    @JsonProperty("ArrivalDateTime")
    public Date arrivalDateTime;
    @JsonProperty("ChangeOfGauge")
    public boolean changeOfGauge;
    @JsonProperty("ActionCode")
    public String actionCode;
    @JsonProperty("ScheduleChangeIndicator")
    public boolean scheduleChangeIndicator;
    @JsonProperty("DepartureAirport")
    public String departureAirport;
    @JsonProperty("MealCode")
    public List<String> mealCode;
    @JsonProperty("ArrivalTerminalName")
    public String arrivalTerminalName;
    @JsonProperty("DisclosureCarrier")
    public DisclosureCarrier disclosureCarrier;
    public int segmentAssociationId;
    @JsonProperty("ClassOfService")
    public String classOfService;
    @JsonProperty("MarketingFlightNumber")
    public int marketingFlightNumber;
    public int sequence;
    @JsonProperty("ArrivalAirport")
    public String arrivalAirport;
    @JsonProperty("MarketingClassOfService")
    public String marketingClassOfService;
}
