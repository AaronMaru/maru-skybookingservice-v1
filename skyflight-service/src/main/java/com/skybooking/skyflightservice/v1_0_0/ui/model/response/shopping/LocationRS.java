package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

@Data
public class LocationRS {

    private String code;
    private String airport;
    private double latitude;
    private double longitude;
    private String city;

}
