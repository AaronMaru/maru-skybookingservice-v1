package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.company;

import com.skybooking.core.validators.annotations.IsEmail;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import com.skybooking.core.validators.annotations.IsNumericString;
import com.skybooking.stakeholderservice.exception.anotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@FieldMatchRequire(first = "businessTypeId", second = "contactPerson", third = "contactPosition", message = "Please provide contact persion and contact position")
@UsernameUnique(first = "phone", second = "code", message = "Phone number already exists")
//@EmailUnique(first = "email", message = "Email already exists")
public class CompanyRQ {

    @BussinessType
    @NotNull(message = "Please provide a business type id")
    private Long businessTypeId;

    @IsNotEmpty
    @CompanyName
    private String businessName;

    private String contactPerson;
    private String contactPosition;

    @IsNotEmpty
    @IsNumericString
    private String phone;

    @IsNotEmpty
    private String code;

    private String email = "";

    @NotNull(message = "Please provide a license")
    private HashMap<Long, MultipartFile> licenses;

    private String address;
    private String website;
    private String postalOrZipCode;
    private String city;

    @NotNull(message = "Please provide a country id")
    @Country
    private Long countryId;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public HashMap<Long, MultipartFile> getLicenses() {
        return licenses;
    }

    public void setLicenses(HashMap<Long, MultipartFile> licenses) {
        this.licenses = licenses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
