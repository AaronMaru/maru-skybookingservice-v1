package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookingHotelHBRS {
    private String checkIn;
    private String checkOut;
    private String code;
    private String name;
    private String categoryCode;
    private String categoryName;
    private String destinationCode;
    private String destinationName;
    private Integer zoneCode;
    private String zoneName;
    private String latitude;
    private String longitude;
    private List<BookingRoomHBRS> rooms;
    private BigDecimal totalNet = BigDecimal.ZERO;
    private String currency;
    private HotelSupplierHBRS supplier;
}
