package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.user;


import lombok.Data;

@Data
public class UserProfileTO {
    private Long userId;
    private Long stakeHolderId;
    private Long companyId;
    private String userRole;
    private String userType;
}
