package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import com.skybooking.skyhotelservice.constant.PaxType;
import lombok.Data;

@Data
public class BookingPaxDSRQ {
    private Integer roomId;
    private PaxType type;
    private Integer age;
    private String name;
    private String surname;
}
