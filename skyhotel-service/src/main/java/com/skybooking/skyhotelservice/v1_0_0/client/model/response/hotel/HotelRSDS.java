package com.skybooking.skyhotelservice.v1_0_0.client.model.response.hotel;

import lombok.Data;

import java.util.List;

@Data
public class HotelRSDS {
    private List<Object> data;
    private Object paging;
}
