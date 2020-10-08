package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.info;

import lombok.Data;

@Data
public class CompanyUserDetailsTO {

    private long id;
    private String name;
    private String code;
    private String thumbnail;
    private String email;
    private String phone;

}
