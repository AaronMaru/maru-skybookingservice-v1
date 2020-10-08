package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CheckRateHBRQ {
    private List<BookingRoomHBRQ> rooms = new ArrayList<>();
}
