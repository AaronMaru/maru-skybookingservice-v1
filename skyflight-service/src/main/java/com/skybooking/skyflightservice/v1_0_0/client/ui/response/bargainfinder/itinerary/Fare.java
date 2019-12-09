package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.itinerary;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fare {

    @JsonProperty("ancillaryFeeGroup")
    private AncillaryFeeGroup ancillaryFeeGroup;

    @JsonProperty("divideByParty")
    private Boolean divideByParty;

    @JsonProperty("eTicketable")
    private Boolean eTicketable;

    @JsonProperty("governingCarriers")
    private String governingCarriers;

    @JsonProperty("lastTicketDate")
    private String lastTicketDate;

    @JsonProperty("offerItemId")
    private String offerItemId;

    @JsonProperty("passengerInfoList")
    private List<PassengerInfoList> passengerInfoList = new ArrayList<>();

    @JsonProperty("reissue")
    private Boolean reissue;

    @JsonProperty("reissueText")
    private String reissueText;

    @JsonProperty("serviceId")
    private String serviceId;

    @JsonProperty("simultaneousReservation")
    private Boolean simultaneousReservation;

    @JsonProperty("spanishFamilyDiscount")
    private String spanishFamilyDiscount;

    @JsonProperty("totalFare")
    private TotalFare totalFare;

    @JsonProperty("validatingCarrierCode")
    private String validatingCarrierCode;

    @JsonProperty("validatingCarriers")
    private List<ValidatingCarrierID> validatingCarriers = new ArrayList<>();

    @JsonProperty("vita")
    private Boolean vita;
}
