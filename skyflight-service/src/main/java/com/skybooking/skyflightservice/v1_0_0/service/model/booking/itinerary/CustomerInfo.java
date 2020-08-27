package com.skybooking.skyflightservice.v1_0_0.service.model.booking.itinerary;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CustomerInfo implements Serializable {

    @JsonProperty("PersonName")
    private List<PersonName> personName;
    @JsonProperty("PaymentInfo")
    private CustomerPaymentInfo paymentInfo;
    @JsonProperty("ContactNumbers")
    private ContactNumbers contactNumbers;
    @JsonProperty("Address")
    private Address address;

}
