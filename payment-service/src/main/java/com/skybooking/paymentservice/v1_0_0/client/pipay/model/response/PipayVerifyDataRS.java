package com.skybooking.paymentservice.v1_0_0.client.pipay.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PipayVerifyDataRS {

    private String processorResponseCode;
    private BigDecimal amount;
    private String currency;

}
