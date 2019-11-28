package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import java.math.BigDecimal;

public class BookingTicketRS {

    private String ticketNumber;
    private String firstName;
    private String lastName;
    private BigDecimal amount;
    private String currency;
    private String passType;

    private BookingBaggageInfoRS bookingBaggageInfoRS;

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public BookingBaggageInfoRS getBookingBaggageInfoRS() {
        return bookingBaggageInfoRS;
    }

    public void setBookingBaggageInfoRS(BookingBaggageInfoRS bookingBaggageInfoRS) {
        this.bookingBaggageInfoRS = bookingBaggageInfoRS;
    }
}
