package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company;

import java.util.ArrayList;
import java.util.List;

public class CompanyRS {

    private Long id;
    private String companyName = "";
    private String profileImg = "";
    private String contactPerson = "";
    private String contactPosition = "";
    private List<LicenseRS> licenses = new ArrayList<>();
    private String address = "";
    private String phone = "";
    private String email = "";
    private String website = "";
    private String postalOrZipCode = "";
    private Long businessTypeId;
    private String businessTypeName;
    private Long countryId;
    private String countryName;
    private String status;
    private String description = "";
    private String city = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProfileImg() {
        profileImg = profileImg != null ? profileImg : "";
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getContactPerson() {
        contactPerson = contactPerson != null ? contactPerson : "";
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPosition() {
        contactPosition = contactPosition != null ? contactPosition : "";
        return contactPosition;
    }

    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition;
    }

    public List<LicenseRS> getLicenses() {
        return licenses;
    }

    public void setLicenses(List<LicenseRS> licenses) {
        this.licenses = licenses;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalOrZipCode() {
        return postalOrZipCode;
    }

    public void setPostalOrZipCode(String postalOrZipCode) {
        this.postalOrZipCode = postalOrZipCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
