package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.stakeholderservice.exception.anotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@FieldMatchRequire(first = "businessTypeId", second = "contactPerson", third = "contactPosition", message = "Please provide contact persion and contact position")
@UsernameUnique(first = "phone", second = "code", message = "Phone number already exists")
public class SkyownerRegisterRQ {

    @Phone
    @NotEmpty(message = "Please provide a phone number")
    private String phone;

    @NotEmpty(message = "Please provide a country code")
    private String code;

    @Email
    private String email;

    @BussinessType
    @NotEmpty(message = "Please provide a business type")
    private Long businessTypeId;

    @NotEmpty(message = "Please provide a business name")
    @CompanyName
    private String businessName;

    private String contactPerson;
    private String contactPosition;

    @NotNull(message = "Please provide a license")
    private HashMap<String, MultipartFile> licenses;

    private String website;
    private String postalOrZipCode;
    private String address;

    @NotNull(message = "Please provide a country id")
    @Country
    private Long countryId;
    private String city;

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

    public Long getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Long businessTypeId) {
        this.businessTypeId = businessTypeId;
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

    public HashMap<String, MultipartFile> getLicenses() {
        return licenses;
    }

    public void setLicenses(HashMap<String, MultipartFile> licenses) {
        this.licenses = licenses;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPostalOrZipCode() {
        return postalOrZipCode;
    }

    public void setPostalOrZipCode(String postalOrZipCode) {
        this.postalOrZipCode = postalOrZipCode;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
