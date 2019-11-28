package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.user;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company.CompanyRS;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsRS {

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

//    private HashMap<String, String> photo = new HashMap<String, String>();
    private String photoMedium;
    private String photoSmall;

    private int status;

    private String address = "";

    private List<CompanyRS> companies = new ArrayList<>();

    private String role = "";

    private List<PermissionRS> permission = new ArrayList<>();

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

    public int getSkyowner() {
        return isSkyowner;
    }

    public void setSkyowner(int skyowner) {
        isSkyowner = skyowner;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
}
