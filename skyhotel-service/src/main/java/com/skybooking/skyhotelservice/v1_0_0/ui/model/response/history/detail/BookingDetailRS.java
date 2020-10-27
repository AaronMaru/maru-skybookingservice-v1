package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class BookingDetailRS {

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
    private ContactInfo contactInfo;
    private PriceInfo priceInfo;
    private HotelInfo hotelInfo;
    private PaymentInfo paymentInfo;
    private List<RoomDetail> room;
    private Integer period = 0;
    private Integer totalExtraBed = 0;
    private String billingAddress = "";
    private String emailAddress = "";
    private Date chargeDate;
    private String clientId = "";
    private String countryResident = "";
    private String remark = "";
    private Integer totalAdult = 0;
    private Integer totalChildren = 0;
    private Integer totalPromotion = 0;


}
