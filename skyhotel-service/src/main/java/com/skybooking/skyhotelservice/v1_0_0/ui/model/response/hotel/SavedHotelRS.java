package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SavedHotelRS {
    private String code;
    private String hotelName;
    private String chainCode;
    private String chainName;
    private String description;
    private Integer ranking;
    private String email;
    private String web;
    private String destination;
    private String thumbnail;
    private Double review;
    private Integer reviewCount;
    private BigDecimal price = BigDecimal.ZERO;
    private String currency;
}
