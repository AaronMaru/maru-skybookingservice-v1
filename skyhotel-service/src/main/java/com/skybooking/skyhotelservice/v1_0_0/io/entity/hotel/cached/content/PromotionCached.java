package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromotionCached implements Serializable {
    private String code;
    private String name;
    private String remark;
}
