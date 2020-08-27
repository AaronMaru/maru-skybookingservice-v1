package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user;

import lombok.Data;

@Data
public class UserReferenceTO {
    private Integer stakeholderUserId;
    private Integer stakeholderCompanyId;
    private String referenceCode;
    private String type;
    private String userRole;
}
