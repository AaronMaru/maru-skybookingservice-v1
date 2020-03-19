package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDetailsTokenRS {

    private String token;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;
    private String code;

    @Column(name = "changed_password")
    private int changedPassword;

    @Column(name = "is_skyowner")
    private int isSkyowner;

    private String gender;

    @Column(name = "user_code")
    private String userCode;

    private String photoMedium;
    private String photoSmall;

    private String address = "";
    private String dob = "";
    private String joined = "";
    private String nationality;
    private String isoCountry = "";
    private BigInteger totalBooking;
    private String typeSky = "";
    private Long currencyId;
    private String curencyCode;

    private List<CompanyRS> companies = new ArrayList<>();

    private String role = "";

    private List<PermissionRS> permission = new ArrayList<>();

}
