package com.skybooking.staffservice.v1_0_0.io.nativeQuery.staff;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class StaffProfileTO {

    private Integer addedBy;
    private String position;
    private BigInteger bookingQty;
    private BigDecimal bookAmount;
    private String joinStatus;
    private String skyuserRole;
    private Date joinDate;

    public BigInteger getBookingQty() {
        return bookingQty;
    }

    public void setBookingQty(BigInteger bookingQty) {
        this.bookingQty = bookingQty;
    }

    public BigDecimal getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(BigDecimal bookAmount) {
        this.bookAmount = bookAmount;
    }

    public String getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(String joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getSkyuserRole() {
        return skyuserRole;
    }

    public void setSkyuserRole(String skyuserRole) {
        this.skyuserRole = skyuserRole;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
