package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingConfirmationRoomHBRQ {
    private String rateKey;
    private List<BookingPaxHBRQ> paxes;
}
