package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company;

import com.skybooking.stakeholderservice.exception.anotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@FieldMatchRequire(first = "businessType", second = "contactPerson", third = "contactPosition", message = "Please provide contact persion and contact position")
@LicenseMatchRequire(first = "businessType", second = "licenseSecond", message = "Please provide secode license")
@UsernameUnique(first = "phone", second = "code", message = "Phone number already exists")
@EmailUnique(first = "email", message = "Email already exists")
public class CompanyRQ {

    @Include(contains = "com_tra|com_biz|com_gov", delimiter = "\\|", message = "HHHHHHH")
    @NotEmpty(message = "Please provide a business type")
    private String businessType;

    @NotEmpty(message = "Please provide a business name")
    @CompanyName
    private String businessName;

    private String contactPerson;
    private String contactPosition;

    @Email
    @NotEmpty(message = "Please provide a email")
    private String email;

    @Phone
    @NotEmpty(message = "Please provide a phone number")
    private String phone;

    @NotEmpty(message = "Please provide a country code")
    private String code;

//    @NotEmpty(message = "Please provide a license")
    @NotNull(message = "Please provide a license")
    private MultipartFile license;
    private MultipartFile licenseSecond;

    private String address;
    private String description;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
