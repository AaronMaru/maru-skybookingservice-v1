package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

import lombok.Data;

@Data
public class StaffRS {

    private Integer companyId;
    private Integer skyuserId;
    private String skyuserRole;
    private String firstName;
    private String lastName;
    private String photo;
    private String userCode;
    private String joinStatus;
    private String joinDate;
    private String position;
    private StaffTotalBookingRS flightBooking;

}
