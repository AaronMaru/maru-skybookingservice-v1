package com.skybooking.skyhotelservice.v1_0_0.client.model.request.availability;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Geolocation {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer radius;
}
