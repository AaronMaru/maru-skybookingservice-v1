package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

@Data
public class BasicRS {

    private String chainCode;
    private String chainName;
    private String hotelName;
    private Integer ranking;
    private String description;
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
