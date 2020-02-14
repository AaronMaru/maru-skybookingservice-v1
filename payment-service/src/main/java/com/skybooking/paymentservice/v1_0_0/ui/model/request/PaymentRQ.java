package com.skybooking.paymentservice.v1_0_0.ui.model.request;

import com.skybooking.paymentservice.exception.annotation.Include;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentRQ {

    @NotBlank(message = "Booking code is required")
    private String bookingCode;

    @NotBlank(message = "Payment code is required")
    private String paymentCode;

    @NotBlank(message = "Product type is required")
    @Include(contains = "FLIGHT|HOTEL", delimiter = "\\|")
    private String productType;
    private String platform;
    private Double paymentAmount;
    private String providerType;
    private String callbackUrl;
    private String token;

}
