package com.skybooking.skyflightservice.constant;

import lombok.Getter;

@Getter
public class CompanyConstant {

    private String name;
    private String address;
    private String city;
    private String postalCode;
    private String street;

    public CompanyConstant() {
        this.name = "SKYBOOKING";
        this.address = "3150 SABRE DRIVE";
        this.city = "PHNOM PENH";
        this.postalCode = "12000";
        this.street = "3150 SABRE DRIVE";
    }
}
