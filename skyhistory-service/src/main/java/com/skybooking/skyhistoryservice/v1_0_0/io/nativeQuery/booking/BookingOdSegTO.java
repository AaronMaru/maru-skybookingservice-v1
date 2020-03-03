package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking;

import java.util.Date;

public class BookingOdSegTO {

    private Integer id;
    private String airlineCode;
    private String airlineName;
    private Integer flightNumber;
    private String equipType;
    private String airCraftName;
    private Date depDateTime;
    private String depLocation;
    private String depLocationName;
    private String depCity;
    private String depCountry;
    private Date arrDateTime;
    private String arrLocation;
    private String arrLocationName;
    private String arrCity;
    private String arrCountry;
    private Integer stop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public String getAirCraftName() {
        return airCraftName;
    }

    public void setAirCraftName(String airCraftName) {
        this.airCraftName = airCraftName;
    }

    public Date getDepDateTime() {
        return depDateTime;
    }

    public void setDepDateTime(Date depDateTime) {
        this.depDateTime = depDateTime;
    }

    public String getDepLocation() {
        return depLocation;
    }

    public void setDepLocation(String depLocation) {
        this.depLocation = depLocation;
    }

    public String getDepLocationName() {
        return depLocationName;
    }

    public void setDepLocationName(String depLocationName) {
        this.depLocationName = depLocationName;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getDepCountry() {
        return depCountry;
    }

    public void setDepCountry(String depCountry) {
        this.depCountry = depCountry;
    }

    public Date getArrDateTime() {
        return arrDateTime;
    }

    public void setArrDateTime(Date arrDateTime) {
        this.arrDateTime = arrDateTime;
    }

    public String getArrLocation() {
        return arrLocation;
    }

    public void setArrLocation(String arrLocation) {
        this.arrLocation = arrLocation;
    }

    public String getArrLocationName() {
        return arrLocationName;
    }

    public void setArrLocationName(String arrLocationName) {
        this.arrLocationName = arrLocationName;
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public String getArrCountry() {
        return arrCountry;
    }

    public void setArrCountry(String arrCountry) {
        this.arrCountry = arrCountry;
    }

    public Integer getStop() {
        return stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }
}
