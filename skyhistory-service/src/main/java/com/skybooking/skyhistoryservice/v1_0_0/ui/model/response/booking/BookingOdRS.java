package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

public class BookingOdRS {

    private Integer elapsedTime;
    private String arrageAirSegs;
    private String grAirSegs;
    private Boolean multipleAirStatus;
    private String multipleAirUrl90;
    private Integer stop;

    private BookingOdSegRS fSegs;


    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getArrageAirSegs() {
        return arrageAirSegs;
    }

    public void setArrageAirSegs(String arrageAirSegs) {
        this.arrageAirSegs = arrageAirSegs;
    }

    public String getGrAirSegs() {
        return grAirSegs;
    }

    public void setGrAirSegs(String grAirSegs) {
        this.grAirSegs = grAirSegs;
    }

    public Boolean getMultipleAirStatus() {
        return multipleAirStatus;
    }

    public void setMultipleAirStatus(Boolean multipleAirStatus) {
        this.multipleAirStatus = multipleAirStatus;
    }

    public String getMultipleAirUrl90() {
        return multipleAirUrl90;
    }

    public void setMultipleAirUrl90(String multipleAirUrl90) {
        this.multipleAirUrl90 = multipleAirUrl90;
    }

    public Integer getStop() {
        return stop;
    }

    public void setStop(Integer stop) {
        this.stop = stop;
    }

    public BookingOdSegRS getfSegs() {
        return fSegs;
    }

    public void setfSegs(BookingOdSegRS fSegs) {
        this.fSegs = fSegs;
    }

}
