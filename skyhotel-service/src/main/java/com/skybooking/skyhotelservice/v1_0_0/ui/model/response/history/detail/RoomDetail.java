package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail;

import lombok.Data;
import java.util.List;

@Data
public class RoomDetail {

    private String thumbnail;
//    @JsonIgnore
    private String code;
    private String description = "";
    private Integer capacity;
    private List<String> specialRequest;
    private List<PassengerInfo> paxInfo;

    public RoomDetail() {}
    public RoomDetail(String thumbnail, String description, Integer capacity, List<String> specialRequest) {
        this.thumbnail = thumbnail;
        this.description = description;
        this.capacity = capacity;
        this.specialRequest = specialRequest;
    }

}
