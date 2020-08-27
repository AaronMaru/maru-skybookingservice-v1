package com.skybooking.skypointservice.v1_0_0.util.auth;

import lombok.Data;

@Data
public class UserToken {
    private Long userId;
    private Long skyuserId;
    private Long companyId;
    private String userRole;
    private String userType;
}
