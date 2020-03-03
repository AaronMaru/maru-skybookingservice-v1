package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.saveFlight;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaveFlightTO {

    private Integer id;

    private String tripType;
    private String className;
    private Integer adult;
    private Integer child;
    private Integer infant;
    private int stopSearch;
    private int multipleAirStatus;
    private BigDecimal amount;
    private String currencyCode;
    private Integer decimalPlaces;

    List<SaveFlightTO> ODInfo = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public int getStopSearch() {
        return stopSearch;
    }

    public void setStopSearch(int stopSearch) {
        this.stopSearch = stopSearch;
    }

    public int getMultipleAirStatus() {
        return multipleAirStatus;
    }

    public void setMultipleAirStatus(int multipleAirStatus) {
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

    public List<SaveFlightTO> getODInfo() {
        return ODInfo;
    }

    public void setODInfo(List<SaveFlightTO> ODInfo) {
        this.ODInfo = ODInfo;
    }
}
