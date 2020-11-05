package com.skybooking.backofficeservice.v1_0_0.client.model.response.hotel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.common.PaymentInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel.hotelDetail.ContactInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel.hotelDetail.HotelInfo;
import com.skybooking.backofficeservice.v1_0_0.ui.model.response.hotel.hotelDetail.PriceInfo;
import lombok.Data;


@Data
public class DetailServiceRS {
    private String bookingCode;
    private String bookingReference;
    private String currencyCode;
    private String status;
    private String checkIn;
    private String checkOut;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private String bookingDate;
    private Long nights;
    private Integer totalRoom;
    private int skypointRedeemed;
    private String cancelationPolicy;
    private HotelInfo hotelInfo;
    private ContactInfo contactInfo;
    private PriceInfo priceInfo;
    private PaymentInfo paymentInfo;

}
