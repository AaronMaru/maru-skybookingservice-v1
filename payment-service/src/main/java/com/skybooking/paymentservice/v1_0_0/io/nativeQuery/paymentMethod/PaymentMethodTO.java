package com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentMethodTO {

    private String type;
    private String code;
    private String method;
    private BigDecimal percentage;
    private BigDecimal percentageBase;
    private Integer paymentId;

}
