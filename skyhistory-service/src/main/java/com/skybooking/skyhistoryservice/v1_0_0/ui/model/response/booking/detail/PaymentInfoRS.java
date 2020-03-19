package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

@Data
public class PaymentInfoRS {

    private String holderName;
    private String bankName;
    private String cardType;
    private String cardNumber;
    private String paymentType;

}
