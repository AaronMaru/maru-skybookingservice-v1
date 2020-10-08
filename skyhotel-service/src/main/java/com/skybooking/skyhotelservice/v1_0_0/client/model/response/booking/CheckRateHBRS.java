package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import lombok.Data;

import java.util.List;

@Data
public class CheckRateHBRS {
    private String code;
    private CheckRateHotelHBRS hotel;
    private List<RatesHBRS> rates;
}
