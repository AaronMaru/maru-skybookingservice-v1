package com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_stop_airports")
public class BookingStopAirportsEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_od_id", nullable = false)
    private BookingOdEntity bookingOdEntity;

    @Column(name = "elapsed_time")
    private Integer elapsedTime;

    @Column(name = "location_code")
    private String locationCode;

    @Column(name = "city")
    private String city;

    @Column(name = "location")
    private String location;

    @Column(name = "arr_date")
    private java.sql.Timestamp arrDate;

    @Column(name = "dep_date")
    private java.sql.Timestamp depDate;

    @Column(name = "duration")
    private String duration;

    @Column(name = "gmt_offset")
    private Integer gmtOffset;

    @Column(name = "equipment")
    private String equipment;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public java.sql.Timestamp getArrDate() {
        return this.arrDate;
    }

    public void setArrDate(java.sql.Timestamp arrDate) {
        this.arrDate = arrDate;
    }

    public java.sql.Timestamp getDepDate() {
        return this.depDate;
    }

    public void setDepDate(java.sql.Timestamp depDate) {
        this.depDate = depDate;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getGmtOffset() {
        return this.gmtOffset;
    }

    public void setGmtOffset(Integer gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public String getEquipment() {
        return this.equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
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
