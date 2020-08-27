package com.skybooking.skyhotelservice.v1_0_0.client.model.response.popularCity;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class PopularCityRSDS {
    private List<PopularCity> data;
    private Object paging;

    @Data
    public static class PopularCity {
        private String destinationCode;
        private String destinationName;
        private BigInteger hotelCount;
        private String thumbnail;
    }
}