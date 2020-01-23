package com.skybooking.skyflightservice.config.company;

import lombok.Getter;

@Getter
public class Company {

    private String name;
    private String address;
    private String city;
    private String postalCode;
    private String street;

    public Company() {
        this.name = "SKYBOOKING";
        this.address = "Phnom Penh";
        this.city = "Phnom Penh";
        this.postalCode = "12000";
        this.street = "360";
    }
}
