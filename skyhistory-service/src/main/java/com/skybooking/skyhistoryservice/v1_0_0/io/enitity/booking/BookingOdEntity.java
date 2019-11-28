package com.skybooking.skyhistoryservice.v1_0_0.io.enitity.booking;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "booking_origin_destinations")
public class BookingOdEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity bookingEntity;

    @OneToMany(mappedBy = "bookingOdEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingStopAirportsEntity> bookingStopAirports;

    @Column(name = "elapsed_time")
    private Integer elapsedTime;

    @Column(name = "group_air_segs")
    private String groupAirSegs;

    @Column(name = "arrage_air_segs")
    @Lob
    private String arrageAirSegs;

    @Column(name = "multiple_air_status")
    private Integer multipleAirStatus;

    @Column(name = "multiple_air_url")
    @Lob
    private String multipleAirUrl;

    @Column(name = "stop")
    private Integer stop;

    @Column(name = "seat_remaining")
    private Integer seatRemaining;

    @Column(name = "cabin_code")
    private String cabinCode;

    @Column(name = "cabin_name")
    private String cabinName;

    @Column(name = "meal")
    private String meal;

    @Column(name = "dep_date")
    private java.sql.Timestamp depDate;

    @Column(name = "arr_date")
    private java.sql.Timestamp arrDate;

    @Column(name = "stop_qty")
    private Integer stopQty;

    @Column(name = "booking_code")
    private String bookingCode;

    @Column(name = "arr_timezone")
    private Integer arrTimezone;

    @Column(name = "dep_timezone")
    private Integer depTimezone;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "airline_code")
    private String airlineCode;

    @Column(name = "airline_name")
    private String airlineName;

    @Column(name = "operated_by_code")
    private String operatedByCode;

    @Column(name = "operated_by_name")
    private String operatedByName;

    @Column(name = "flight_number")
    private Integer flightNumber;

    @Column(name = "marriage_grp")
    private String marriageGrp;

    @Column(name = "equip_type")
    private String equipType;

    @Column(name = "air_craft_name")
    private String airCraftName;

    @Column(name = "dep_location_code")
    private String depLocationCode;

    @Column(name = "dep_airport_name")
    private String depAirportName;

    @Column(name = "dep_terminal")
    private String depTerminal;

    @Column(name = "dep_city")
    private String depCity;

    @Column(name = "dep_latitude")
    private String depLatitude;

    @Column(name = "dep_longitude")
    private String depLongitude;

    @Column(name = "arr_location_code")
    private String arrLocationCode;

    @Column(name = "arr_airport_name")
    private String arrAirportName;

    @Column(name = "arr_terminal")
    private String arrTerminal;

    @Column(name = "arr_city")
    private String arrCity;

    @Column(name = "arr_latitude")
    private String arrLatitude;

    @Column(name = "arr_longitude")
    private String arrLongitude;

    @Column(name = "layover")
    private Integer layover;

    @Column(name = "parent_id")
    private Integer parentId;

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

    public String getArrageAirSegs() {
        return this.arrageAirSegs;
    }

    public void setArrageAirSegs(String arrageAirSegs) {
        this.arrageAirSegs = arrageAirSegs;
    }

    public Integer getMultipleAirStatus() {
        return this.multipleAirStatus;
    }

    public void setMultipleAirStatus(Integer multipleAirStatus) {
        this.multipleAirStatus = multipleAirStatus;
    }

    public Integer getStop() {
        return this.stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }

    public Integer getSeatRemaining() {
        return this.seatRemaining;
    }

    public void setSeatRemaining(Integer seatRemaining) {
        this.seatRemaining = seatRemaining;
    }

    public String getCabinCode() {
        return this.cabinCode;
    }

    public void setCabinCode(String cabinCode) {
        this.cabinCode = cabinCode;
    }

    public String getCabinName() {
        return this.cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public String getMeal() {
        return this.meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public java.sql.Timestamp getDepDate() {
        return this.depDate;
    }

    public void setDepDate(java.sql.Timestamp depDate) {
        this.depDate = depDate;
    }

    public java.sql.Timestamp getArrDate() {
        return this.arrDate;
    }

    public void setArrDate(java.sql.Timestamp arrDate) {
        this.arrDate = arrDate;
    }

    public Integer getStopQty() {
        return this.stopQty;
    }

    public void setStopQty(Integer stopQty) {
        this.stopQty = stopQty;
    }

    public String getBookingCode() {
        return this.bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public Integer getArrTimezone() {
        return this.arrTimezone;
    }

    public void setArrTimezone(Integer arrTimezone) {
        this.arrTimezone = arrTimezone;
    }

    public Integer getDepTimezone() {
        return this.depTimezone;
    }

    public void setDepTimezone(Integer depTimezone) {
        this.depTimezone = depTimezone;
    }

    public Integer getMileage() {
        return this.mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getAirlineCode() {
        return this.airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getAirlineName() {
        return this.airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getOperatedByCode() {
        return this.operatedByCode;
    }

    public void setOperatedByCode(String operatedByCode) {
        this.operatedByCode = operatedByCode;
    }

    public String getOperatedByName() {
        return this.operatedByName;
    }

    public void setOperatedByName(String operatedByName) {
        this.operatedByName = operatedByName;
    }

    public Integer getFlightNumber() {
        return this.flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getMarriageGrp() {
        return this.marriageGrp;
    }

    public void setMarriageGrp(String marriageGrp) {
        this.marriageGrp = marriageGrp;
    }

    public String getEquipType() {
        return this.equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public String getAirCraftName() {
        return this.airCraftName;
    }

    public void setAirCraftName(String airCraftName) {
        this.airCraftName = airCraftName;
    }

    public String getDepLocationCode() {
        return this.depLocationCode;
    }

    public void setDepLocationCode(String depLocationCode) {
        this.depLocationCode = depLocationCode;
    }

    public String getDepAirportName() {
        return this.depAirportName;
    }

    public void setDepAirportName(String depAirportName) {
        this.depAirportName = depAirportName;
    }

    public String getDepTerminal() {
        return this.depTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    public String getDepCity() {
        return this.depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getDepLatitude() {
        return this.depLatitude;
    }

    public void setDepLatitude(String depLatitude) {
        this.depLatitude = depLatitude;
    }

    public String getDepLongitude() {
        return this.depLongitude;
    }

    public void setDepLongitude(String depLongitude) {
        this.depLongitude = depLongitude;
    }

    public String getArrLocationCode() {
        return this.arrLocationCode;
    }

    public void setArrLocationCode(String arrLocationCode) {
        this.arrLocationCode = arrLocationCode;
    }

    public String getArrAirportName() {
        return this.arrAirportName;
    }

    public void setArrAirportName(String arrAirportName) {
        this.arrAirportName = arrAirportName;
    }

    public String getArrTerminal() {
        return this.arrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

    public String getArrCity() {
        return this.arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public String getArrLatitude() {
        return this.arrLatitude;
    }

    public void setArrLatitude(String arrLatitude) {
        this.arrLatitude = arrLatitude;
    }

    public String getArrLongitude() {
        return this.arrLongitude;
    }

    public void setArrLongitude(String arrLongitude) {
        this.arrLongitude = arrLongitude;
    }

    public Integer getLayover() {
        return this.layover;
    }

    public void setLayover(Integer layover) {
        this.layover = layover;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public String getGroupAirSegs() {
        return groupAirSegs;
    }

    public void setGroupAirSegs(String groupAirSegs) {
        this.groupAirSegs = groupAirSegs;
    }

    public String getMultipleAirUrl() {
        return multipleAirUrl;
    }

    public void setMultipleAirUrl(String multipleAirUrl) {
        this.multipleAirUrl = multipleAirUrl;
    }

}
