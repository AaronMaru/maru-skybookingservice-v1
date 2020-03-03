package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;

import javax.persistence.Column;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsTokenRS {

    private String token;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;
    private String code;

    @Column(name = "changed_password")
    private int changedPassword;

    @Column(name = "is_skyowner")
    private int isSkyowner;

    private String gender;

    @Column(name = "user_code")
    private String userCode;

    private String photoMedium;
    private String photoSmall;

//    private int status;

    private String address = "";
    private String dob = "";
    private String joined = "";
    private String nationality;
    private BigInteger totalBooking;
    private String typeSky = "";
    private Long currencyId;
    private String curencyCode;

    private List<CompanyRS> companies = new ArrayList<>();

    private String role = "";

    private List<PermissionRS> permission = new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getChangedPassword() {
        return changedPassword;
    }

    public void setChangedPassword(int changedPassword) {
        this.changedPassword = changedPassword;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<CompanyRS> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyRS> companies) {
        this.companies = companies;
    }

    public String getPhotoMedium() {
        return photoMedium;
    }

    public void setPhotoMedium(String photoMedium) {
        this.photoMedium = photoMedium;
    }

    public String getPhotoSmall() {
        return photoSmall;
    }

    public void setPhotoSmall(String photoSmall) {
        this.photoSmall = photoSmall;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PermissionRS> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionRS> permission) {
        this.permission = permission;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoined() {
        return joined;
    }

    public void setJoined(String joined) {
        this.joined = joined;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public BigInteger getTotalBooking() {
        return totalBooking;
    }

    public void setTotalBooking(BigInteger totalBooking) {
        this.totalBooking = totalBooking;
    }

    public int getIsSkyowner() {
        return isSkyowner;
    }

    public void setIsSkyowner(int isSkyowner) {
        this.isSkyowner = isSkyowner;
    }

    public String getTypeSky() {
        return typeSky;
    }

    public void setTypeSky(String typeSky) {
        this.typeSky = typeSky;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurencyCode() {
        return curencyCode;
    }

    public void setCurencyCode(String curencyCode) {
        this.curencyCode = curencyCode;
    }
}
