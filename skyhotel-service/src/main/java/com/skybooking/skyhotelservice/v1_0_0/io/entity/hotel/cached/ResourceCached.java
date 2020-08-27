package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceCached implements Serializable {

    private ImageUrlCached imageUrl;

    @Data
    public static class ImageUrlCached implements Serializable {
        private String small;
        private String medium;
        private String bigger;
    }
}
