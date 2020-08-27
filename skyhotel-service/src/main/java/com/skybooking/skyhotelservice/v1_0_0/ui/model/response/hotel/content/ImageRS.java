package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import lombok.Data;

@Data
public class ImageRS {

    private String thumbnail;
    private String thumbnailUrl;
    private String typeCode;
    private Integer sortOrder;
    private String roomCode;
    private String roomType;
    private String characteristicCode;

}
