package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

import java.util.List;

public class StaffRS {

    private Integer companyId;
    private Integer skyuserId;
    private String skyuserRole;
    private String firstName;
    private String lastName;
    private String photoMedium;
    private String userCode;

    private List<StaffTotalBookingRS> bookingDetail;

    public String getSkyuserRole() {
        return skyuserRole;
    }

    public void setSkyuserRole(String skyuserRole) {
        this.skyuserRole = skyuserRole;
    }

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

    public String getPhotoMedium() {
        return photoMedium;
    }

    public void setPhotoMedium(String photoMedium) {
        this.photoMedium = photoMedium;
    }

    public List<StaffTotalBookingRS> getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(List<StaffTotalBookingRS> bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSkyuserId() {
        return skyuserId;
    }

    public void setSkyuserId(Integer skyuserId) {
        this.skyuserId = skyuserId;
    }

}
