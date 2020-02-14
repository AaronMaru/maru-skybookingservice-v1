package com.skybooking.paymentservice.v1_0_0.client.pipay.model.request;

import lombok.Data;

@Data
public class PipayVerifyRQ {

    private PipayVerifyDataRQ data;

    public PipayVerifyRQ(PipayVerifyDataRQ data) {
        this.data = data;
    }
}
