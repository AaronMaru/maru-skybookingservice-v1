package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.Data;

@Data
public class ReserveDSRQ {
    private ReserveHotelDSRQ hotel;
    private String clientReference;
}
