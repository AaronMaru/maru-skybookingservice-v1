package com.skybooking.backofficeservice.v1_0_0.client.model.response.account;

import lombok.Data;

import java.util.List;

@Data
public class AccountDetailServiceRS {
    private String firstName;
    private String lastName;
    private String email;
    private String code;
    private String phone;
    private Boolean changedPassword;
    private Boolean isSkyowner;
    private String userCode;
    private String photoSmall;
    private String address;
    private String dob;
    private String joined;
    private String nationality;
    private String isoCountry;
    private Integer totalBooking;
    private String role;
    private String curencyCode;
    private List<Companies> companies;

}
