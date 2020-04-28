package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.payment;

import lombok.Data;

@Data
public class PaymentMethodTO {

    private String type;
    private String code;
    private String method;
    private Integer paymentId;

}
