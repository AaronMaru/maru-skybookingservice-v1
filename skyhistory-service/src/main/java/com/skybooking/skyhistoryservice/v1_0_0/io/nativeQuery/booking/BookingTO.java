package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class BookingTO {

    private Integer bookingId;
    private String bookingCode;
    private String transId;
    private String payMethod;
    private String pnrCode;
    private String tripType;
    private String staffName;
    private BigInteger adult;
    private BigInteger child;
    private BigInteger infant;
    private BigInteger totalPass;
    private String cabinName;
    private BigDecimal totalAmount;
    private BigDecimal markupAmount;
    private BigDecimal markupPayAmount;
    private BigDecimal disPayment;
    private Date bookDate;
    private Date depDate;
    private String currencyCode;
    private String contName;
    private String itineraryFile;
    private String itineraryPath;
    private String recieptFile;
    private String recieptPath;
    private String bookType;
    private Byte status;
    private String statusKey;
    private String contLocationCode;
    private String contPhone;
    private String contPhoneCode;
    private String contEmail;

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

    public Date getDepDate() {
        return depDate;
    }

    public void setDepDate(Date depDate) {
        this.depDate = depDate;
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

    public String getItineraryFile() {
        return itineraryFile;
    }

    public void setItineraryFile(String itineraryFile) {
        this.itineraryFile = itineraryFile;
    }

    public String getItineraryPath() {
        return itineraryPath;
    }

    public void setItineraryPath(String itineraryPath) {
        this.itineraryPath = itineraryPath;
    }

    public String getRecieptFile() {
        return recieptFile;
    }

    public void setRecieptFile(String recieptFile) {
        this.recieptFile = recieptFile;
    }

    public String getRecieptPath() {
        return recieptPath;
    }

    public void setRecieptPath(String recieptPath) {
        this.recieptPath = recieptPath;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
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

    public String getContLocationCode() {
        return contLocationCode;
    }

    public void setContLocationCode(String contLocationCode) {
        this.contLocationCode = contLocationCode;
    }

    public String getContPhone() {
        return contPhone;
    }

    public void setContPhone(String contPhone) {
        this.contPhone = contPhone;
    }

    public String getContPhoneCode() {
        return contPhoneCode;
    }

    public void setContPhoneCode(String contPhoneCode) {
        this.contPhoneCode = contPhoneCode;
    }

    public String getContEmail() {
        return contEmail;
    }

    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    public BigDecimal getMarkupAmount() {
        return markupAmount;
    }

    public void setMarkupAmount(BigDecimal markupAmount) {
        this.markupAmount = markupAmount;
    }

    public BigDecimal getMarkupPayAmount() {
        return markupPayAmount;
    }

    public void setMarkupPayAmount(BigDecimal markupPayAmount) {
        this.markupPayAmount = markupPayAmount;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
}
