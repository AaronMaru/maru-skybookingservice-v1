package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import lombok.Data;

import java.io.Serializable;

@Data
public class BoardCached implements Serializable {
    private String code;
    private String description;
}
