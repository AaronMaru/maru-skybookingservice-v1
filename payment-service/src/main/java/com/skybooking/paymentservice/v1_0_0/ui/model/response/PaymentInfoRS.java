package com.skybooking.paymentservice.v1_0_0.ui.model.response;

import lombok.Data;

@Data
public class PaymentInfoRS {

    private Long id;
    private String type;
    private String code;
    private String method;
    private Double percentage;
    private Integer paymentId;

}
