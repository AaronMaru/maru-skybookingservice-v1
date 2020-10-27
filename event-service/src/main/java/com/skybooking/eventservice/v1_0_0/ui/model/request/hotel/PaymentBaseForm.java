package com.skybooking.eventservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentBaseForm {

    private String bookingCode = "";
    private String hotelName = "";
    private Integer period = 0;
    private String roomType = "";
    private Integer numRooms = 0;
    private Integer numExtraBed = 0;
    private BigDecimal totalRoomCharges = BigDecimal.ZERO;
    private BigDecimal totalExtraBedCharges = BigDecimal.ZERO;
    private BigDecimal grandTotal = BigDecimal.ZERO;
    private BigDecimal totalPaidToCreditCard = BigDecimal.ZERO;

}
