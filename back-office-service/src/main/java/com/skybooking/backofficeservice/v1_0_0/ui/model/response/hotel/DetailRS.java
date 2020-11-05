package com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.common.PaymentInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel.hotelDetail.*;
import lombok.Data;

import java.util.List;

@Data
public class DetailRS {
    private String checkIn;
    private String checkOut;
    private String reference;
    private String bookingReference;
    private String bookingCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private String bookingDate;
    private ContactInfo contactInfo;
    private HotelInfo hotelInfo;
    private List<RoomInfo> roomInfo;
    private List<PaxInfo> paxInfo;
    private PaymentInfo paymentInfo;
    private PriceInfo priceInfo;
    private String currencyCode;
    private Long nights;
    private Integer totalRoom;
}
