package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class HistoryBookingDetailRS {

    private String bookingCode = "";
    private String bookingReference = "";
    private String currencyCode = "";
    private String status = "";
    private String checkIn;
    private String checkOut;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private String bookingDate;
    private Long nights;
    private Integer totalRoom = 0;
    private int skypointRedeemed = 0;
    private String cancelationPolicy = "";
    private HotelInfo hotelInfo;
    private ContactInfo contactInfo;
    private PriceInfo priceInfo;
    private PaymentInfo paymentInfo;
    private List<RoomDetail> rooms;

}
