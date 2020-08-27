package com.skybooking.skyflightservice.v1_0_0.service.model;

public class ShareFlightRS {
    private String status;

    public ShareFlightRS() {
    }

    public ShareFlightRS(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
