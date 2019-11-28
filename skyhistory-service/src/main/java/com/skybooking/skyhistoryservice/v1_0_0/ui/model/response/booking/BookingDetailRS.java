package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingDetailRS {

    private Integer bookingId;
    private String bookingCode;
    private String pnrCode;
    private String tripType;
    private String staffName;
    private BigInteger adult;
    private BigInteger child;
    private BigInteger infant;
    private BigInteger totalPass;
    private String cabinName;
    private BigDecimal totalAmount;
    private BigDecimal disPayment;
    private Date bookDate;
    private String currencyCode;
    private String contName;
    private Byte status;
    private String statusKey;

    List<BookingOdRS> bookingOd = new ArrayList<>();
    List<BookingTicketRS> airTickets = new ArrayList<>();
    List<BookingAirItinPriceRS> airItinPrices = new ArrayList<>();


    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getPnrCode() {
        return pnrCode;
    }

    public void setPnrCode(String pnrCode) {
        this.pnrCode = pnrCode;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public BigInteger getAdult() {
        return adult;
    }

    public void setAdult(BigInteger adult) {
        this.adult = adult;
    }

    public BigInteger getChild() {
        return child;
    }

    public void setChild(BigInteger child) {
        this.child = child;
    }

    public BigInteger getInfant() {
        return infant;
    }

    public void setInfant(BigInteger infant) {
        this.infant = infant;
    }

    public BigInteger getTotalPass() {
        return totalPass;
    }

    public void setTotalPass(BigInteger totalPass) {
        this.totalPass = totalPass;
    }

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getDisPayment() {
        return disPayment;
    }

    public void setDisPayment(BigDecimal disPayment) {
        this.disPayment = disPayment;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getContName() {
        return contName;
    }

    public void setContName(String contName) {
        this.contName = contName;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(String statusKey) {
        this.statusKey = statusKey;
    }

    public List<BookingOdRS> getBookingOd() {
        return bookingOd;
    }

    public void setBookingOd(List<BookingOdRS> bookingOd) {
        this.bookingOd = bookingOd;
    }

    public List<BookingTicketRS> getAirTickets() {
        return airTickets;
    }

    public void setAirTickets(List<BookingTicketRS> airTickets) {
        this.airTickets = airTickets;
    }

    public List<BookingAirItinPriceRS> getAirItinPrices() {
        return airItinPrices;
    }

    public void setAirItinPrices(List<BookingAirItinPriceRS> airItinPrices) {
        this.airItinPrices = airItinPrices;
    }
}
