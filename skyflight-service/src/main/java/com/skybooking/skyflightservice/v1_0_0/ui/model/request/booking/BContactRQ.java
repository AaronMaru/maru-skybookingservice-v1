package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class BContactRQ {

    @NotBlank(message = "Contact person's name is required.")
    private String name;

    @NotBlank(message = "Contact person's email address is required.")
    @Email
    private String email;

    @NotBlank(message = "Contact person's phone code is required.")
    private String phoneCode;

    @NotBlank(message = "Contact person's phone number is required.")
    private String phoneNumber;

}
