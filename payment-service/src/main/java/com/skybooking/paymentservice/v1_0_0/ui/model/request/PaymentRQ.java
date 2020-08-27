package com.skybooking.paymentservice.v1_0_0.ui.model.request;

import com.skybooking.paymentservice.exception.annotation.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@HotelCodeValidate(first = "bookingCode", second = "productType", message = "The booking code is invalid", statuses = {"PENDING", "PAYMENT_SELECTED", "PAYMENT_PROCESSING", "PAYMENT_CREATED"})
@FlightCodeValidate(first = "bookingCode", second = "productType", message = "The booking code is invalid", statuses = {0, 2, 3, 4, 5, 6})
public class PaymentRQ {

    @NotBlank(message = "Booking code is required")
    private String bookingCode;

    @NotBlank(message = "Payment code is required")
    @PaymentCodeValidate
    private String paymentCode;

    @NotBlank(message = "Product type is required")
    @Include(contains = "FLIGHT|HOTEL|POINT", delimiter = "\\|")
    private String productType;
    private String platform;
    private Double paymentAmount;
    private String providerType;
    private String callbackUrl;
    private String token;

}
