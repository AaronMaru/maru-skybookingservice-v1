package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FlightSaveRS {

    private Integer id;
    private String tripType;
    private String className;
    private Integer adult;
    private Integer child;
    private Integer infant;
    private Integer stopSearch;
    private Integer multipleAirStatus;
    private BigDecimal amount;
    private String currencyCode;
    private Integer decimalPlaces;
    List<FlightSaveODRS> ODInfo = new ArrayList<>();

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getAdult() {
        return adult;
    }

    public void setAdult(Integer adult) {
        this.adult = adult;
    }

    public Integer getChild() {
        return child;
    }

    public void setChild(Integer child) {
        this.child = child;
    }

    public Integer getInfant() {
        return infant;
    }

    public void setInfant(Integer infant) {
        this.infant = infant;
    }

    public Integer getStopSearch() {
        return stopSearch;
    }

    public void setStopSearch(Integer stopSearch) {
        this.stopSearch = stopSearch;
    }

    public Integer getMultipleAirStatus() {
        return multipleAirStatus;
    }

    public void setMultipleAirStatus(Integer multipleAirStatus) {
        this.multipleAirStatus = multipleAirStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<FlightSaveODRS> getODInfo() {
        return ODInfo;
    }

    public void setODInfo(List<FlightSaveODRS> ODInfo) {
        this.ODInfo = ODInfo;
    }

}
