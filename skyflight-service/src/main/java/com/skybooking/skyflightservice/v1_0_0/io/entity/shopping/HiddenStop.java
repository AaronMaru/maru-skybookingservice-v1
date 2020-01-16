package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;

import java.io.Serializable;

@Data
public class HiddenStop implements Serializable {

    private String airport;
    private String city;
    private String country;
    private String arrivalTime;
    private String departureTime;
    private long duration;

}
