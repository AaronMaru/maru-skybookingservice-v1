package com.skybooking.skyhotelservice.v1_0_0.client.model.response.similarhotel;

import lombok.Data;

import java.util.List;

@Data
public class SimilarRSDS {

    private int status;
    private String message = "";
    private List<Integer> data;

    @Data
    public static class HotelCode {
        private List<Integer> hotelCode;
    }
}
