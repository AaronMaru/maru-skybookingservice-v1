package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AmenityCached implements Serializable {
    private String code;
    private String icon;
    private String name;
    private String keyword;
}
