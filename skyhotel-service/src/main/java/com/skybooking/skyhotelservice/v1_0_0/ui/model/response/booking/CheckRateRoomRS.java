package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking;

import com.skybooking.skyhotelservice.constant.RoomStatus;
import lombok.Data;

import java.util.List;

@Data
public class CheckRateRoomRS {
    private Number id;
    private RoomStatus status;
    private String code;
    private String name;
    private String supplierReference;
    private List<RoomPaxRS> pax;
    private List<CheckRateRoomRateRS> rates;
}
