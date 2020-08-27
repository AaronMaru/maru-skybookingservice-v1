package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;

@Data
public class SegmentCached implements Serializable {
    private Integer code;
    private String description;
}
