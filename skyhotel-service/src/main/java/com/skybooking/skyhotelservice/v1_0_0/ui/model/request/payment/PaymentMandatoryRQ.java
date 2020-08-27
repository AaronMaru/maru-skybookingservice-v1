package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.payment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class PaymentMandatoryRQ {

    @NotBlank(message = "Booking code is required")
    private String bookingCode;

    @NotBlank(message = "Payment code is required")
    private String paymentCode;

    private BigDecimal percentage;

    private BigDecimal percentageBase;

}
