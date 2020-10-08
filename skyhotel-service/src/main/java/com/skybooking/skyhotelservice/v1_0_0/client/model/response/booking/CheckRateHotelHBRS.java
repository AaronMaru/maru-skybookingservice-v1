package com.skybooking.skyhotelservice.v1_0_0.client.model.response.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckRateHotelHBRS {
    private String checkIn;
    private String checkOut;
    private Integer code;
    private String name;
    private String description;
    private Integer exclusiveDeal;
    private String categoryCode;
    private String categoryName;
    private String destinationCode;
    private String destinationName;
    private Integer zoneCode;
    private String zoneName;
    private String latitude;
    private String longitude;
    private String gaita;
    private String minRate;
    private String maxRate;
    private Number totalSellingRate;
    private BigDecimal totalNet = BigDecimal.ZERO;
    private BigDecimal pendingAmount = BigDecimal.ZERO;
    private String currency;
    private HotelSupplierHBRS supplier;
    private String clientComments;
    private BigDecimal cancellationAmount = BigDecimal.ZERO;
    private List<HotelReviewHBRS> reviews;
    private List<CheckRateRoomHBRS> rooms;
}
