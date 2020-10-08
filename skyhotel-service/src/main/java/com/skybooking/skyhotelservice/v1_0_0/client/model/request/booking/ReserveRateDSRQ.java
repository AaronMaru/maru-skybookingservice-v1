package com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking;

import lombok.Data;

import java.util.List;

@Data
public class ReserveRateDSRQ {
    private String key;
    private Integer roomId;
    private String classCode;
    private String boardCode;
    private List<BookingPaxDSRQ> paxes;
}
