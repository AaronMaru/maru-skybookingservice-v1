package com.skybooking.skypointservice.v1_0_0.client.stakeholder.model.response;

import lombok.Data;

@Data
public class UserReferenceRS {
    private Integer stakeholderUserId;
    private Integer stakeholderCompanyId;
    private String userCode;
    private String name;
    private String email;
    private String phoneCode;
    private String phoneNumber;
    private String type;
    private String userRole;
}
