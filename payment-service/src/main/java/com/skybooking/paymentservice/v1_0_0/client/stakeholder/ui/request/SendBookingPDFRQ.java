package com.skybooking.paymentservice.v1_0_0.client.stakeholder.ui.request;

public class SendBookingPDFRQ {

    private String bookingCode;
    private String email;
    private Integer skyuserId;
    private Integer companyId;

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSkyuserId() {
        return skyuserId;
    }

    public void setSkyuserId(Integer skyuserId) {
        this.skyuserId = skyuserId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public SendBookingPDFRQ(String bookingCode, String email, Integer skyuserId, Integer companyId) {
        this.bookingCode = bookingCode;
        this.email = email;
        this.skyuserId = skyuserId;
        this.companyId = companyId;
    }
}
