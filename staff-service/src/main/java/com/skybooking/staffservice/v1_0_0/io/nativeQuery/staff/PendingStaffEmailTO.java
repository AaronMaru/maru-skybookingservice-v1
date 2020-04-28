package com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff;

import lombok.Data;

import java.util.Date;

@Data
public class PendingStaffEmailTO {

    private Integer id;
    private String username;
    private String role;
    private Date invitedAt;
    private Boolean isAvailable;

}
