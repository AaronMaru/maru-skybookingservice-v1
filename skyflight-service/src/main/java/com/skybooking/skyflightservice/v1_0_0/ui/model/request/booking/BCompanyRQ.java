package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BCompanyRQ {

    @NotBlank(message = "company's name is required.")
    private String name;

    @NotBlank(message = "company's address is required.")
    private String address;

    @NotBlank(message = "company's city name is required.")
    private String city;

    @NotBlank(message = "company's postal code is required.")
    private String postalCode;

    @NotBlank(message = "company's street address number is required.")
    private String street;

}
