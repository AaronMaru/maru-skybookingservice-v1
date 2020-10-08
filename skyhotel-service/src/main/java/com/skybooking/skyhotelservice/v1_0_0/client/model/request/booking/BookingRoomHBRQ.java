package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import com.skybooking.skyhotelservice.constant.RateType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRoomHBRQ {
    private String rateKey;
    private RateType rateType;
}
