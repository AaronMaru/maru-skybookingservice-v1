package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking;

public class BookingOdTO {

    private Integer id;
    private Integer elapsedTime;
    private String arrageAirSegs;
    private String grAirSegs;
    private Boolean multipleAirStatus;
    private String multipleAirUrl90;
    private Integer stop;

    private BookingOdSegTO bookingOdSegTO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public BookingOdSegTO getBookingOdSegTO() {
        return bookingOdSegTO;
    }

    public void setBookingOdSegTO(BookingOdSegTO bookingOdSegTO) {
        this.bookingOdSegTO = bookingOdSegTO;
    }
}
