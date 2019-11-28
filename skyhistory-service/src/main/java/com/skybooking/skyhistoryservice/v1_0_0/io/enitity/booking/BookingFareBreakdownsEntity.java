package com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "booking_fare_breakdowns")
public class BookingFareBreakdownsEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "travel_itin_id", nullable = false)
    private BookingTravelItinerariesEntity bookingTravelItineraries;

    @Column(name = "cabin")
    private String cabin;

    @Column(name = "code")
    private String code;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "pass_type")
    private String passType;

    @Column(name = "fare_type")
    private String fareType;

    @Column(name = "filing_carrier")
    private String filingCarrier;

    @Column(name = "global_ind")
    private String globalInd;

    @Column(name = "market")
    private String market;

    @Column(name = "bag_allowance")
    private String bagAllowance;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Column(name = "updated_at")
    private java.util.Date updatedAt;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCabin() {
        return this.cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPassType() {
        return this.passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getFareType() {
        return this.fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getFilingCarrier() {
        return this.filingCarrier;
    }

    public void setFilingCarrier(String filingCarrier) {
        this.filingCarrier = filingCarrier;
    }

    public String getGlobalInd() {
        return this.globalInd;
    }

    public void setGlobalInd(String globalInd) {
        this.globalInd = globalInd;
    }

    public String getMarket() {
        return this.market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getBagAllowance() {
        return this.bagAllowance;
    }

    public void setBagAllowance(String bagAllowance) {
        this.bagAllowance = bagAllowance;
    }

    public java.util.Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
