package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LocationRSDS {

    private String code;
    private String areaCode;
    private String areaName;
    private String cityName;
    private String address;
    private Double longitude;
    private Double latitude;

}
