package com.skybooking.skyflightservice.v1_0_0.service.model.booking.offlineItinerary.travelitinerary.customerinfo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class PaymentTA implements Serializable {
    private PaymentFormTA form;
}
