package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.company;

public class CompanyRS {

    private Long id;
    private String companyName = "";
    private String profileImg = "";
    private String contactPerson = "";
    private String contactPosition = "";
//    private List<LicenseRS> licenses = new ArrayList<>();
    private String address = "";
    private String phone = "";
    private String webiste = "";
    private String postalOrZipCode = "";

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

//    public List<LicenseRS> getLicenses() {
//        return licenses;
//    }
//
//    public void setLicenses(List<LicenseRS> licenses) {
//        this.licenses = licenses;
//    }

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

    public String getWebiste() {
        return webiste;
    }

    public void setWebiste(String webiste) {
        this.webiste = webiste;
    }

    public String getPostalOrZipCode() {
        return postalOrZipCode;
    }

    public void setPostalOrZipCode(String postalOrZipCode) {
        this.postalOrZipCode = postalOrZipCode;
    }

}
