package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Air implements Serializable {

    @JsonProperty("ArrivalAirportCodeContext")
    public String arrivalAirportCodeContext;
    @JsonProperty("DepartureTerminalName")
    public String departureTerminalName;
    @JsonProperty("StopQuantity")
    public int stopQuantity;
    @JsonProperty("ActionCode")
    public String actionCode;
    @JsonProperty("HiddenStop")
    public List<HiddenStop> hiddenStop;
    @JsonProperty("Informational")
    public boolean informational;
    @JsonProperty("MarketingAirlineCode")
    public String marketingAirlineCode;
    @JsonProperty("ArrivalDateTime")
    public String arrivalDateTime;
    public boolean inboundConnection;
    @JsonProperty("SpecialMeal")
    public boolean specialMeal;
    public int id;
    @JsonProperty("ScheduleChangeIndicator")
    public boolean scheduleChangeIndicator;
    @JsonProperty("OperatingFlightNumber")
    public String operatingFlightNumber;
    @JsonProperty("DepartureAirport")
    public String departureAirport;
    @JsonProperty("DepartureAirportCodeContext")
    public String departureAirportCodeContext;
    @JsonProperty("Code")
    public String code;
    @JsonProperty("MarketingFlightNumber")
    public String marketingFlightNumber;
    public int sequence;
    @JsonProperty("DepartureDateTime")
    public String departureDateTime;
    @JsonProperty("SegmentBookedDate")
    public Date segmentBookedDate;
    @JsonProperty("Eticket")
    public boolean eticket;
    @JsonProperty("MarriageGrp")
    public MarriageGrp marriageGrp;
    @JsonProperty("Cabin")
    public Cabin cabin;
    @JsonProperty("EquipmentType")
    public String equipmentType;
    @JsonProperty("DepartureTerminalCode")
    public String departureTerminalCode;
    @JsonProperty("ElapsedTime")
    public double elapsedTime;
    @JsonProperty("Seats")
    public String seats;
    @JsonProperty("Meal")
    public Meal meal = new Meal();
    @JsonProperty("OperatingAirlineCode")
    public String operatingAirlineCode;
    @JsonProperty("CodeShare")
    public boolean codeShare;
    @JsonProperty("ResBookDesigCode")
    public String resBookDesigCode;
    @JsonProperty("AirMilesFlown")
    public String airMilesFlown;
    @JsonProperty("NumberInParty")
    public int numberInParty;
    @JsonProperty("FunnelFlight")
    public boolean funnelFlight;
    @JsonProperty("OperatingClassOfService")
    public String operatingClassOfService;
    @JsonProperty("OperatingAirlineShortName")
    public String operatingAirlineShortName;
    @JsonProperty("ChangeOfGauge")
    public boolean changeOfGauge;
    @JsonProperty("FlightNumber")
    public int flightNumber;
    @JsonProperty("DayOfWeekInd")
    public int dayOfWeekInd;
    @JsonProperty("ArrivalAirport")
    public String arrivalAirport;
    @JsonProperty("Banner")
    public String banner;
    @JsonProperty("SmokingAllowed")
    public boolean smokingAllowed;
    public int segmentAssociationId;
    @JsonProperty("SegmentSpecialRequests")
    public SegmentSpecialRequests segmentSpecialRequests;
    @JsonProperty("ClassOfService")
    public String classOfService;
    public boolean isPast;
    @JsonProperty("MarketingClassOfService")
    public String marketingClassOfService;
    @JsonProperty("AirlineRefId")
    public String airlineRefId;
    public boolean outboundConnection;
    @JsonProperty("ArrivalTerminalCode")
    public String arrivalTerminalCode;
    @JsonProperty("ArrivalTerminalName")
    public String arrivalTerminalName;

}
