package com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "flight_saves")

public class FlightSaveEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "flightSaveEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<FlightSaveODEntity> flightSaveODEntities;

    @Column(name = "trip_type", nullable = false, length = 10)
    private String tripType;

    @Column(name = "class_code", nullable = false, length = 2)
    private String classCode;

    @Column(name = "class_name", nullable = false, length = 20)
    private String className;

    @Column(name = "adult", nullable = false)
    private Integer adult;

    @Column(name = "child")
    private Integer child;

    @Column(name = "infant")
    private Integer infant;

    @Column(name = "stop_search")
    private Integer stopSearch = 0;

    @Column(name = "multiple_air_status", nullable = false)
    private Integer multipleAirStatus = 0;

    @Column(name = "multiple_air_logo90", nullable = false)
    private String multipleAirLogo90 = "";

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "tag_id")
    @Lob
    private String tagId;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount = BigDecimal.valueOf(0.00);

    @Column(name = "currency_code", nullable = false)
    private String currencyCode = "USD";

    @Column(name = "decimal_places", nullable = false)
    private Integer decimalPlaces = 2;

    @Column(name = "baseFare", nullable = false)
    private BigDecimal baseFare = BigDecimal.valueOf(0.00);


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTripType() {
        return this.tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getClassCode() {
        return this.classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getAdult() {
        return this.adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public Integer getChild() {
        return this.child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public Integer getInfant() {
        return this.infant;
    }

    public void setInfant(Integer infant) {
        this.infant = infant;
    }

    public Integer getStopSearch() {
        return this.stopSearch;
    }

    public void setStopSearch(Integer stopSearch) {
        this.stopSearch = stopSearch;
    }

    public Integer getMultipleAirStatus() {
        return this.multipleAirStatus;
    }

    public void setMultipleAirStatus(Integer multipleAirStatus) {
        this.multipleAirStatus = multipleAirStatus;
    }

    public String getMultipleAirLogo90() {
        return this.multipleAirLogo90;
    }

    public void setMultipleAirLogo90(String multipleAirLogo90) {
        this.multipleAirLogo90 = multipleAirLogo90;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public java.util.Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getTagId() {
        return this.tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getDecimalPlaces() {
        return this.decimalPlaces;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public BigDecimal getBaseFare() {
        return this.baseFare;
    }

    public void setBaseFare(BigDecimal baseFare) {
        this.baseFare = baseFare;
    }

    public List<FlightSaveODEntity> getFlightSaveODEntities() {
        return flightSaveODEntities;
    }

    public void setFlightSaveODEntities(List<FlightSaveODEntity> flightSaveODEntities) {
        this.flightSaveODEntities = flightSaveODEntities;
    }

    @Override
    public String toString() {
        return "FlightSaveEntity{" +
                "id=" + id +
                ", tripType='" + tripType + '\'' +
                ", classCode='" + classCode + '\'' +
                ", className='" + className + '\'' +
                ", adult=" + adult +
                ", child=" + child +
                ", infant=" + infant +
                ", stopSearch=" + stopSearch +
                ", multipleAirStatus=" + multipleAirStatus +
                ", multipleAirLogo90='" + multipleAirLogo90 + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", companyId=" + companyId +
                ", tagId='" + tagId + '\'' +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", decimalPlaces=" + decimalPlaces +
                ", baseFare=" + baseFare +
                '}';
    }
}
