package com.skybooking.skyflightservice.v1_0_0.service.model.security;

import lombok.Data;

@Data
public class UserAuthenticationMetaTA {

    private Integer userId;
    private Integer stakeholderId;
    private Integer companyId;
    private String userType;
    private boolean authenticated;

}
