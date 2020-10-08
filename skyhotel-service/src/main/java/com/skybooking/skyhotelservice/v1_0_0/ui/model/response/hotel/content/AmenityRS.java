package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AmenityRS {
    @JsonProperty(value = "code")
    private String code;
    @JsonProperty(value = "icon")
    private String icon;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "keyword")
    private String keyword;
}
