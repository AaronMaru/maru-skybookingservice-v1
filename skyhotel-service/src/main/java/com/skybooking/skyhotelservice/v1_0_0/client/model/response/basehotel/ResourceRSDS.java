package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel;

import lombok.Data;

@Data
public class ResourceRSDS {
    private ImageUrl imageUrl;

    @Data
    public static class ImageUrl {
        private String small;
        private String medium;
        private String bigger;
    }
}
