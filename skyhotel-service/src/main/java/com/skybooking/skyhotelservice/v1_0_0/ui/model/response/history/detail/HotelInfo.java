package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail;

import lombok.Data;

@Data
public class HotelInfo {
    private String thumbnail;
    private String hotelName = "";
    private String hotelLocation = "";
    private String interestPointDistance;
    private String interestPointName;
    private String interestPointDistanceUnit;
}
