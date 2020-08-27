package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageCached implements Serializable {

    private String thumbnail;
    private String thumbnailUrl;
    private String typeCode;
    private Integer sortOrder;
    private String roomCode;
    private String roomType;
    private String characteristicCode;

}
