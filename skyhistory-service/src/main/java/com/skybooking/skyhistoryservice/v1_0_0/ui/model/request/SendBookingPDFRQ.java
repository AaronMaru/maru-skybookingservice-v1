package com.skybooking.skyhistoryservice.v1_0_0.ui.model.request;

public class SendBookingPDFRQ {

    private String bookingCode;
    private String email;

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

}
