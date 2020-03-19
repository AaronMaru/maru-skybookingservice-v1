package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class StaffProfileRS {

    private String firstName;
    private String lastName;
    private String userCode;
    private String contPhone;
    private String contEmail;
    private String addedBy = "";
    private String position;
    private BigInteger bookingQty;
    private BigDecimal bookAmount;
    private String joinStatus;
    private String skyuserRole;
    private String joinDate;
    private String photoMedium;
    private String photoSmall;

}
