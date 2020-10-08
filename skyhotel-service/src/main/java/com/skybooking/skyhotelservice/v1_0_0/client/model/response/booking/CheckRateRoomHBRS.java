package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import com.skybooking.skyhotelservice.constant.RoomStatus;
import lombok.Data;

import java.util.List;


@Data
public class CheckRateRoomHBRS {
    private Number id;
    private RoomStatus status;
    private String code;
    private String name;
    private String supplierReference;
    private List<RoomPaxHBRS> pax;
    private List<CheckRateRoomRateHBRS> rates;
}
