package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Reservation implements Serializable {
    @JsonProperty("Remarks")
    private Remarks remarks;
    @JsonProperty("Addresses")
    private Addresses addresses;
    @JsonProperty("BookingDetails")
    private BookingDetails bookingDetails;
    @JsonProperty("AccountingLines")
    private AccountingLines accountingLines;
    @JsonProperty("ReceivedFrom")
    private ReceivedFrom receivedFrom;
    private int numberInParty;
    @JsonProperty("POS")
    private POS pOS;
    @JsonProperty("EmailAddresses")
    private String emailAddresses;
    @JsonProperty("GenericSpecialRequests")
    private List<GenericSpecialRequest> genericSpecialRequests;
    private int numberOfInfants;
    @JsonProperty("PassengerReservation")
    private PassengerReservation passengerReservation;
    @JsonProperty("NumberInSegment")
    private int numberInSegment;
    @JsonProperty("PhoneNumbers")
    private PhoneNumbers phoneNumbers;
    @JsonProperty("OpenReservationElements")
    private OpenReservationElements openReservationElements;
}



