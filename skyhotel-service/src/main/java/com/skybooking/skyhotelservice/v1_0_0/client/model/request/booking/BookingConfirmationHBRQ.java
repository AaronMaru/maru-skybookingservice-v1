package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.Data;

import java.util.List;

@Data
public class BookingConfirmationHBRQ {
    private String code;
    private HolderHBRQ holder;
    private List<BookingConfirmationRoomHBRQ> rooms;
    private String clientReference = "Skybooking";
    private Integer tolerance = 0;
    private PaymentInfoHBRQ paymentInfo;
}
