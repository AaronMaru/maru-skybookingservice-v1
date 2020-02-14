package com.skybooking.paymentservice.v1_0_0.client.pipay.model.response;

import lombok.Data;

@Data
public class PipayVerifyRS {

    private String resultCode;
    private PipayVerifyDataRS data;

}
