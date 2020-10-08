package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import com.skybooking.skyhotelservice.constant.PaxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingPaxHBRQ {
    private Integer roomId;
    private PaxType type;
    private Integer age;
    private String name;
    private String surname;
}
