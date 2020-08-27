package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.ResourceRSDS;
import lombok.Data;

@Data
public class ResourceRS {
    private ResourceRSDS.ImageUrl imageUrl;

    @Data
    public static class ImageUrl {
        private String small;
        private String medium;
        private String bigger;
    }
}
