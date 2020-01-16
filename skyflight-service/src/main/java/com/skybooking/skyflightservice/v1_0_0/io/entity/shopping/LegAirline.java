package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class LegAirline implements Serializable {

    private String airline;
    private String aircraft;
    private String flightNumber;

}
