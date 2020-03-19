package com.skybooking.paymentservice.v1_0_0.io.nativeQuery.paymentMethod;

import lombok.Data;

@Data
public class PaymentCodeTO {

    private Integer id;
    private String bookingCode;
    private String paymentCode;

}
