package com.skybooking.backofficeservice.v1_0_0.client.model.response.account;

import lombok.Data;

import java.awt.*;
import java.math.BigInteger;

@Data
public class Companies {
    private BigInteger id;
    private String companyName;
    private String profileImg;
    private String contactPerson;
    private String address;
    private String phone;
    private String email;
    private BigInteger businessTypeId;
    private String businessTypeName;
    private BigInteger countryId;
    private String countryName;
    private String status;
    private String description;
    private String city;
    private BigInteger totalBooking;
}
