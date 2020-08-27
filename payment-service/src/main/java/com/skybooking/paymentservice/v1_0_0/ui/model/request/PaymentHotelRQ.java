package com.skybooking.paymentservice.v1_0_0.ui.model.request;

import com.skybooking.paymentservice.exception.annotation.BookingCodeValidate;
import com.skybooking.paymentservice.exception.annotation.HotelCodeValidate;
import com.skybooking.paymentservice.exception.annotation.Include;
import com.skybooking.paymentservice.exception.annotation.PaymentCodeValidate;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentHotelRQ {
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
