package com.skybooking.skyhotelservice.v1_0_0.client.model.response.hotel;

import lombok.Data;

import java.util.List;

@Data
public class DestinationHotelCodeRSDS {
    private Hotel data;

    @Data
    public static class Hotel {
        private List<Integer> codes;
    }
}
