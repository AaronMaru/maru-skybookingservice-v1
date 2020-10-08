package com.skybooking.eventservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PaymentSuccessRQ extends PaymentBaseForm {

    @Email
    @NotNull(message = "The email code can not be null")
    @NotEmpty(message = "The email code can not be empty")
    private String email = "";

}
