package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FlightLeg implements Serializable {

    private String origin;
    private String destination;
    private Date date;

    public FlightLeg(String origin, String destination, Date date) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

}
