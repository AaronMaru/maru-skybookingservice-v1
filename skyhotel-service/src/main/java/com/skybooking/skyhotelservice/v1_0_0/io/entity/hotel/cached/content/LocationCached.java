package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class LocationCached implements Serializable {

    private String code;
    private String areaCode;
    private String areaName;
    private String cityName;
    private String address;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String distance = "0.0";

}
