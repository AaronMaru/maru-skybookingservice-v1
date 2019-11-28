package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.stakeholderservice.exception.anotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@UsernameUnique(first = "username", second = "code", message = "Username already exists")
@FieldMatchRequire(first = "businessType", second = "contactPerson", third = "contactPosition", message = "Please provide contact persion and contact position")
@LicenseMatchRequire(first = "businessType", second = "licenseSecond", message = "Please provide secode license")
public class SkyownerRegisterRQ {

    @Email
    @NotEmpty(message = "Please provide a email")
    private String username;

    @NotEmpty(message = "Please provide a first name")
    private String firstName;

    @NotEmpty(message = "Please provide a last name")
    private String lastName;

    @Phone
    @NotEmpty(message = "Please provide a phone number")
    private String phone;

    @NotEmpty(message = "Please provide a country code")
    private String code;

    @NotEmpty(message = "Please provide a password")
    @Size(min = 6, max = 25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*?[#?!@$%^&*-]).+$", message = "Invalid password")
    private String password;

    @NotEmpty(message = "Please provide a confirm password")
    private String confirmPassword;

    @Include(contains = "com_tra|com_biz|com_gov", delimiter = "\\|")
    @NotEmpty(message = "Please provide a business type")
    private String businessType;

    @NotEmpty(message = "Please provide a business name")
    @CompanyName
    private String businessName;

    private String contactPerson;
    private String contactPosition;

//    @NotEmpty(message = "Please provide a license")
    @NotNull(message = "Please provide a license")
    private MultipartFile license;
    private MultipartFile licenseSecond;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
