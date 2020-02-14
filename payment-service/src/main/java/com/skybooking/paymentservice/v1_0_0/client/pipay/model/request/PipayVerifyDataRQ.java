package com.skybooking.paymentservice.v1_0_0.client.pipay.model.request;

import lombok.Data;

@Data
public class PipayVerifyDataRQ {

    private String orderID;
    private String processorID;

    public PipayVerifyDataRQ(String orderID, String processorID) {
        this.orderID = orderID;
        this.processorID = processorID;
    }
}
