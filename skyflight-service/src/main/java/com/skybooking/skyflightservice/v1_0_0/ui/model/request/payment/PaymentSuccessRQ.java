package com.skybooking.skyflightservice.v1_0_0.ui.model.request.payment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentSuccessRQ {

    @NotBlank(message = "Booking code is required")
    private String bookingCode;

}
