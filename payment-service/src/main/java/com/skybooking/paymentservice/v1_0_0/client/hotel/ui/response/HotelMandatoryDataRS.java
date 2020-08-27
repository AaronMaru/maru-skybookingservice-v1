package com.skybooking.paymentservice.v1_0_0.client.hotel.ui.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelMandatoryDataRS {

    private BigDecimal amount;
    private Integer paymentId;
    private String name;
    private String email;
    private String phoneNumber;
    private Integer skyuserId;
    private Integer companyId;
    private Integer bookingId;
    private String bookingCode;
    private String Description;

}
