package com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flight_save_origin_destinations")
public class FlightSaveODEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_save_id", nullable = false)
    private FlightSaveEntity flightSaveEntity;

    @Column(name = "elapsed_time", nullable = false)
    private Integer elapsedTime;

    @Column(name = "airline_code", nullable = false, length = 3)
    private String airlineCode;

    @Column(name = "airline_name", nullable = false, length = 191)
    private String airlineName;

    @Column(name = "airline_logo_45", nullable = false, length = 191)
    private String airlineLogo45;

    @Column(name = "airline_logo_90", nullable = false, length = 191)
    private String airlineLogo90;

    @Column(name = "d_date_time", nullable = false)
    private Date dDateTime;

    @Column(name = "a_date_time", nullable = false)
    private Date aDateTime;

    @Column(name = "o_location", nullable = false, length = 3)
    private String oLocation;

    @Column(name = "d_location", nullable = false, length = 3)
    private String dLocation;

    @Column(name = "o_location_name", nullable = false, length = 191)
    private String oLocationName;

    @Column(name = "d_location_name", nullable = false, length = 191)
    private String dLocationName;

    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Column(name = "updated_at")
    private java.util.Date updatedAt;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "stop", nullable = false)
    private Integer stop = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlightSaveEntity getFlightSaveEntity() {
        return flightSaveEntity;
    }

    public void setFlightSaveEntity(FlightSaveEntity flightSaveEntity) {
        this.flightSaveEntity = flightSaveEntity;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineLogo45() {
        return airlineLogo45;
    }

    public void setAirlineLogo45(String airlineLogo45) {
        this.airlineLogo45 = airlineLogo45;
    }

    public String getAirlineLogo90() {
        return airlineLogo90;
    }

    public void setAirlineLogo90(String airlineLogo90) {
        this.airlineLogo90 = airlineLogo90;
    }

    public Date getdDateTime() {
        return dDateTime;
    }

    public void setdDateTime(Date dDateTime) {
        this.dDateTime = dDateTime;
    }

    public Date getaDateTime() {
        return aDateTime;
    }

    public void setaDateTime(Date aDateTime) {
        this.aDateTime = aDateTime;
    }

    public String getoLocation() {
        return oLocation;
    }

    public void setoLocation(String oLocation) {
        this.oLocation = oLocation;
    }

    public String getdLocation() {
        return dLocation;
    }

    public void setdLocation(String dLocation) {
        this.dLocation = dLocation;
    }

    public String getoLocationName() {
        return oLocationName;
    }

    public void setoLocationName(String oLocationName) {
        this.oLocationName = oLocationName;
    }

    public String getdLocationName() {
        return dLocationName;
    }

    public void setdLocationName(String dLocationName) {
        this.dLocationName = dLocationName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStop() {
        return stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "FlightSaveODEntity{" +
                "id=" + id +
                ", elapsedTime=" + elapsedTime +
                ", airlineCode='" + airlineCode + '\'' +
                ", airlineName='" + airlineName + '\'' +
                ", airlineLogo45='" + airlineLogo45 + '\'' +
                ", airlineLogo90='" + airlineLogo90 + '\'' +
                ", dDateTime=" + dDateTime +
                ", aDateTime=" + aDateTime +
                ", oLocation='" + oLocation + '\'' +
                ", dLocation='" + dLocation + '\'' +
                ", oLocationName='" + oLocationName + '\'' +
                ", dLocationName='" + dLocationName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userId=" + userId +
                ", stop=" + stop +
                '}';
    }
}
