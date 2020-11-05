package com.skybooking.backofficeservice.v1_0_0.io.nativeQuery.search;

import lombok.Data;

import java.util.Date;

@Data
public class SearchDataTO {
    private String code;
    private String reference;
    private String fullName;
    private String email;
    private String phoneCode;
    private String phone;
    private String type;
    private Date createdAt;
}
