package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel;

import lombok.Data;

@Data
public class ResourceRS {
    private ImageUrl imageUrl;

    @Data
    public static class ImageUrl {
        private String small;
        private String medium;
        private String bigger;
    }
}
