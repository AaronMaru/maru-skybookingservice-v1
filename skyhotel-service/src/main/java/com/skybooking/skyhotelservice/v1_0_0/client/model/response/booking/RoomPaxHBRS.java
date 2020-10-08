package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import com.skybooking.skyhotelservice.constant.PaxType;
import lombok.Data;

@Data
public class RoomPaxHBRS {
    private Integer roomId;
    private PaxType type;
    private Integer age;
    private String name;
    private String surname;
}
