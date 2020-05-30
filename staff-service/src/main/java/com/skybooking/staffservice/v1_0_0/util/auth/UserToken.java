package com.skybooking.staffservice.v1_0_0.util.auth;

import lombok.Data;

@Data
public class UserToken {
    private Long userId;
    private String fullName;
    private Long stakeholderId;
    private Long companyId;
    private String userRole;
}
