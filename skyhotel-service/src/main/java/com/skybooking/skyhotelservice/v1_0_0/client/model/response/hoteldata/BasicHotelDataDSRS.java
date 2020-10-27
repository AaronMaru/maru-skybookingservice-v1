package com.skybooking.skyhotelservice.v1_0_0.client.model.response.hoteldata;

import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail.RoomDetail;
import lombok.Data;

import java.util.List;

@Data
public class BasicHotelDataDSRS {

    private Integer code;
    private String hotelName;
    private String description;
    private Integer ranking;
    private String email;
    private String web;
    private String destination;
    private String thumbnail;
    private String interestPointDistance;
    private String interestPointName;
    private String interestPointDistanceUnit;
    private List<RoomDetail> rooms;
}
