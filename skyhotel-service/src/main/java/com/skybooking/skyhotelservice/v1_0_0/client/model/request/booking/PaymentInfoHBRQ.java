package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoHBRQ {
    private String method;
    private String cardType;
    private String cardNumber;
    private String cardHolderName;
    private String remark;
}
