package com.skybooking.skyflightservice.v1_0_0.ui.model.request.ticketing;

public class IssueTicketRQ {
    private String itineraryNo;
    private Integer passengerQualifier;
    private String bookingCode;

    public String getItineraryNo() {
        return itineraryNo;
    }

    public void setItineraryNo(String itineraryNo) {
        this.itineraryNo = itineraryNo;
    }

    public Integer getPassengerQualifier() {
        return passengerQualifier;
    }

    public void setPassengerQualifier(Integer passengerQualifier) {
        this.passengerQualifier = passengerQualifier;
    }


    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

}
