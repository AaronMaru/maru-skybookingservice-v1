package com.skybooking.skyhotelservice.v1_0_0.client.model.request.event;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentSuccessRQEV {

    private String email = "";
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
    private String lang;

}
