package com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "booking_travel_itineraries")
public class BookingTravelItinerariesEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity bookingEntity;

    @OneToMany(mappedBy = "bookingTravelItineraries", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingFareBreakdownsEntity> bookingFareBreakdowns;

    @Column(name = "pass_type")
    private String passType;

    @Column(name = "pass_qty")
    private Integer passQty;

    @Column(name = "private_fare")
    private String privateFare;

    @Column(name = "base_fare")
    private BigDecimal baseFare;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "total_tax")
    private BigDecimal totalTax;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "non_refundable_ind")
    private String nonRefundableInd;

    @Column(name = "baggage_info")
    @Lob
    private String baggageInfo;

    @Column(name = "fare_calculation")
    @Lob
    private String fareCalculation;

    @Column(name = "endorsements")
    @Lob
    private String endorsements;

    @Column(name = "noted")
    @Lob
    private String noted;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "piece_status")
    private Integer pieceStatus;

    @Column(name = "bag_airline")
    private String bagAirline;

    @Column(name = "bag_piece")
    private Integer bagPiece;

    @Column(name = "bag_weight")
    private Integer bagWeight;

    @Column(name = "bag_unit")
    private String bagUnit;

    @Column(name = "com_percentage")
    private BigDecimal comPercentage;

    @Column(name = "com_amount")
    private BigDecimal comAmount;

    @Column(name = "com_mkamount")
    private BigDecimal comMkamount;

    @Column(name = "com_source")
    private String comSource;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassType() {
        return this.passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public Integer getPassQty() {
        return this.passQty;
    }

    public void setPassQty(Integer passQty) {
        this.passQty = passQty;
    }

    public String getPrivateFare() {
        return this.privateFare;
    }

    public void setPrivateFare(String privateFare) {
        this.privateFare = privateFare;
    }

    public BigDecimal getBaseFare() {
        return this.baseFare;
    }

    public void setBaseFare(BigDecimal baseFare) {
        this.baseFare = baseFare;
    }

    public BigDecimal getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalTax() {
        return this.totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getNonRefundableInd() {
        return this.nonRefundableInd;
    }

    public void setNonRefundableInd(String nonRefundableInd) {
        this.nonRefundableInd = nonRefundableInd;
    }

    public String getBaggageInfo() {
        return this.baggageInfo;
    }

    public void setBaggageInfo(String baggageInfo) {
        this.baggageInfo = baggageInfo;
    }

    public String getFareCalculation() {
        return this.fareCalculation;
    }

    public void setFareCalculation(String fareCalculation) {
        this.fareCalculation = fareCalculation;
    }

    public String getEndorsements() {
        return this.endorsements;
    }

    public void setEndorsements(String endorsements) {
        this.endorsements = endorsements;
    }

    public String getNoted() {
        return this.noted;
    }

    public void setNoted(String noted) {
        this.noted = noted;
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

    public Integer getPieceStatus() {
        return this.pieceStatus;
    }

    public void setPieceStatus(Integer pieceStatus) {
        this.pieceStatus = pieceStatus;
    }

    public String getBagAirline() {
        return this.bagAirline;
    }

    public void setBagAirline(String bagAirline) {
        this.bagAirline = bagAirline;
    }

    public Integer getBagPiece() {
        return this.bagPiece;
    }

    public void setBagPiece(Integer bagPiece) {
        this.bagPiece = bagPiece;
    }

    public Integer getBagWeight() {
        return this.bagWeight;
    }

    public void setBagWeight(Integer bagWeight) {
        this.bagWeight = bagWeight;
    }

    public String getBagUnit() {
        return this.bagUnit;
    }

    public void setBagUnit(String bagUnit) {
        this.bagUnit = bagUnit;
    }

    public BigDecimal getComPercentage() {
        return this.comPercentage;
    }

    public void setComPercentage(BigDecimal comPercentage) {
        this.comPercentage = comPercentage;
    }

    public BigDecimal getComAmount() {
        return this.comAmount;
    }

    public void setComAmount(BigDecimal comAmount) {
        this.comAmount = comAmount;
    }

    public BigDecimal getComMkamount() {
        return this.comMkamount;
    }

    public void setComMkamount(BigDecimal comMkamount) {
        this.comMkamount = comMkamount;
    }

    public String getComSource() {
        return this.comSource;
    }

    public void setComSource(String comSource) {
        this.comSource = comSource;
    }
}
