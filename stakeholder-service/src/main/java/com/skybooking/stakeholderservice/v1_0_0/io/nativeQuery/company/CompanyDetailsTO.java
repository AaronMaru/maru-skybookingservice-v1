package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.company;

import lombok.Data;

@Data
public class CompanyDetailsTO {

    private Integer stakeholderUserId;
    private Integer stakeholderCompanyId;
    private String companyName;
    private String companyCode;
    private String thumbnail;
    private String email;
    private String phone;

}
