package com.skybooking.paymentservice.v1_0_0.client.flight.ui.request;

import com.skybooking.paymentservice.v1_0_0.client.model.request.ProductPaymentTransactionRQ;
import lombok.Data;

@Data
public class FlightPaymentSucceedRQ extends ProductPaymentTransactionRQ {

    private int status = 1;
    private String email;
    private Integer skyuserId;
    private Integer companyId;
}
