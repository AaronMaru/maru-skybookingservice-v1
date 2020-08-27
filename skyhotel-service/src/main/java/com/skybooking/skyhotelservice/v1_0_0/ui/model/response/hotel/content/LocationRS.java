package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationRS {

    private String code;
    private String areaCode;
    private String areaName;
    private String cityName;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String distance;

}
