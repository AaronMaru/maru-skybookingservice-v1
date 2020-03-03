package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.flightSave;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FlightSaveODRS {

    private Integer elapsedTime;
    private String airlineCode;
    private String airlineName;
    private String airlineLogo45;
    private String airlineLogo90;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String dDateTime;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String aDateTime;

    private String oLocation;
    private String dLocation;
    private String oLocationName;
    private String dLocationName;
    private Integer stop;

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

    public String getdDateTime() {
        return dDateTime;
    }

    public void setdDateTime(String dDateTime) {
        this.dDateTime = dDateTime;
    }

    public String getaDateTime() {
        return aDateTime;
    }

    public void setaDateTime(String aDateTime) {
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

    public Integer getStop() {
        return stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }
}
