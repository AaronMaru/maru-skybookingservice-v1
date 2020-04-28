package com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class StaffProfileTO {

    private String addedBy;
    private String position;
    private BigInteger bookingQty;
    private BigDecimal bookAmount;
    private String joinStatus;
    private String skyuserRole;
    private Date joinDate;

}
