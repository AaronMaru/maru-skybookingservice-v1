package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

@Data
public class RoomDetail {
    private String thumbnail;
    @JsonIgnore
    private String code;
    private String type;
    private Integer capacity;
    private List<String> specialRequest;

    public RoomDetail() {}
    public RoomDetail(String thumbnail, String type, Integer capacity, List<String> specialRequest) {
        this.thumbnail = thumbnail;
        this.type = type;
        this.capacity = capacity;
        this.specialRequest = specialRequest;
    }
}
