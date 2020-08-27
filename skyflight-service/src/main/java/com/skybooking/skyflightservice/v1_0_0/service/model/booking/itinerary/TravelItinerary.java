package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TravelItinerary implements Serializable {

    @JsonProperty("CustomerInfo")
    private CustomerInfo customerInfo;
    @JsonProperty("AccountingInfo")
    private List<AccountingInfo> accountingInfo;
    @JsonProperty("RemarkInfo")
    private RemarkInfo remarkInfo;
    @JsonProperty("ItineraryInfo")
    private ItineraryInfo itineraryInfo;
    @JsonProperty("OpenReservationElements")
    private OpenReservationElements openReservationElements;
    @JsonProperty("ItineraryRef")
    private ItineraryRef itineraryRef;
    @JsonProperty("SpecialServiceInfo")
    private List<SpecialServiceInfo> specialServiceInfo;

}
