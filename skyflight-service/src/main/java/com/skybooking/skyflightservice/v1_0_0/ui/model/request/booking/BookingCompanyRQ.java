package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BookingCompanyRQ {

    @NotBlank(message = "companyConstant's name is required.")
    private String name;

    @NotBlank(message = "companyConstant's address is required.")
    private String address;

    @NotBlank(message = "companyConstant's city name is required.")
    private String city;

    @NotBlank(message = "companyConstant's postal code is required.")
    private String postalCode;

    @NotBlank(message = "companyConstant's street address number is required.")
    private String street;

}
