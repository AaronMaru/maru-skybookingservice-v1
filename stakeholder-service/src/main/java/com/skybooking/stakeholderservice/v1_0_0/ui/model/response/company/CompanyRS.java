package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company;

public class CompanyRS {

    private Long id;
    private String companyName;
    private String profileImg;
    private String typeValue;
    private String contactPerson;
    private String contactPosition;
    private String License;
    private String LicenseSecond;
    private String address;
    private String email;
    private String phone;


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

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
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

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public String getLicenseSecond() {
        LicenseSecond = LicenseSecond != null ? LicenseSecond : "";
        return LicenseSecond;
    }

    public void setLicenseSecond(String licenseSecond) {
        LicenseSecond = licenseSecond;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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


}
