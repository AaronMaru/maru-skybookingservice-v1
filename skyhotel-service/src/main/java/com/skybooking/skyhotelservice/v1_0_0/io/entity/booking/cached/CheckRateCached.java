package com.skybooking.skyhotelservice.v1_0_0.io.entity.booking.cached;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckRateCached implements Serializable {
    private String requestId;
    private String checkIn;
    private String checkOut;
    private Integer hotelCode;
    private HolderCached holder;
    private List<BookingRateCached> rooms;
}
