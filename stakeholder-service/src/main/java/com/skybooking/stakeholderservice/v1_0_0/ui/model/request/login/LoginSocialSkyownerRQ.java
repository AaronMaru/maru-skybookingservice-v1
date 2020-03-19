package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login;

import com.skybooking.stakeholderservice.exception.anotation.CompanyName;
import com.skybooking.stakeholderservice.exception.anotation.Include;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LoginSocialSkyownerRQ {

    @NotEmpty(message = "Please provide username")
    private String username;

    @NotEmpty(message = "Please provide provider id")
    private String providerId;

    @NotEmpty(message = "Please provide provider")
    @Include(contains = "facebook|google|apple", delimiter = "\\|")
    private String provider;

    @NotEmpty(message = "Please provide first name")
    private String firstName;

    @NotEmpty(message = "Please provide last name")
    private String lastName;

    private String photoUrl;
    private String password;

    @Include(contains = "com_tra|com_biz|com_gov", delimiter = "\\|")
    @NotEmpty(message = "Please provide a business type")
    private String businessType;

    @NotEmpty(message = "Please provide a business name")
    @CompanyName
    private String businessName;

    private String contactPerson;
    private String contactPosition;

    @NotNull(message = "Please provide a license")
    private MultipartFile license;
    private MultipartFile licenseSecond;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPosition() {
        return contactPosition;
    }

    public void setContactPosition(String contactPosition) {
        this.contactPosition = contactPosition;
    }

    public MultipartFile getLicense() {
        return license;
    }

    public void setLicense(MultipartFile license) {
        this.license = license;
    }

    public MultipartFile getLicenseSecond() {
        return licenseSecond;
    }

    public void setLicenseSecond(MultipartFile licenseSecond) {
        this.licenseSecond = licenseSecond;
    }

}
