package com.skybooking.skygatewayservice.model;

import lombok.Data;

@Data
public class UserTokenModel {
    private String clientId;
    private Integer userId;
    private Integer stakeholderId;
    private Integer companyId;
    private String userRole;
}
