package com.skybooking.skyhistoryservice.v1_0_0.util.auth;

import lombok.Data;

@Data
public class UserToken {
    private Long userId;
    private Long stakeholderId;
    private Long companyId;
    private String userRole;
}
