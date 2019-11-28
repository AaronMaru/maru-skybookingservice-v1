package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

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
    private String statusKey;
    private String skyuserRole;
    private Date joinDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getContPhone() {
        return contPhone;
    }

    public void setContPhone(String contPhone) {
        this.contPhone = contPhone;
    }

    public String getContEmail() {
        return contEmail;
    }

    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

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

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
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

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }



}
