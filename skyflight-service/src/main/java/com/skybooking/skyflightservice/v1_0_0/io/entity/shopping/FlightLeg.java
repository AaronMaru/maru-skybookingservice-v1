package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class FlightLeg implements Serializable {

    private String origin;
    private String destination;
    private LocalDate date;

    public FlightLeg(String origin, String destination, LocalDate date) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

}
