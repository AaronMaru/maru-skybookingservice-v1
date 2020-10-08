package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.booking;

import com.skybooking.skyhotelservice.constant.PaxType;
import lombok.Data;

@Data
public class BookingPaxRQ {
    private Integer roomId;
    private PaxType type;
    private Integer age;
    private String name;
    private String surname;
}
