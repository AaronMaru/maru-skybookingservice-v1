package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;

@Data
public class BasicCached implements Serializable {

    private String chainCode;
    private String chainName;
    private String hotelName;
    private Integer ranking;
    private String email;
    private String web;
    private Integer badge;
    private Integer star;
    private String categoryCode;
    private String categoryGroupCode;
    private String groupCategoryName;
    private String accommodationTypeCode;
    private String accommodationTypeName;

}
