package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.Data;

import java.util.List;

@Data
public class ReserveRoomDSRQ {
    private String typeCode;
    private List<ReserveRateDSRQ> rates;
}
