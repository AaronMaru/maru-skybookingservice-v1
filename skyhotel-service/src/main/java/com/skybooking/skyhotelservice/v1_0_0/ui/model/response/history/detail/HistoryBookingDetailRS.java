package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class HistoryBookingDetailRS {

    private String bookingCode = "";
    private String bookingReference = "";
    private String contactName = "";
    private String contactPhone = "";
    private String contactEmail = "";
    private BigDecimal cost = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private String currencyCode = "";
    private String status = "";
    private Date checkIn;
    private Date checkOut;
    private String hotelName = "";
    private String hotelLocation = "";
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date bookingDate;
    private Long nights;
    private Integer totalRoom = 0;
    private String thumbnail;
    private String payTo = "SKYBOOKING";
    private String paymentOf;
    private String paymentId;
    private BigDecimal taxFeeAmount = BigDecimal.ZERO;
    private BigDecimal vatAmount = BigDecimal.ZERO;
    private BigDecimal accommodationTaxAmount = BigDecimal.ZERO;
    private BigDecimal paymentFeeAmount = BigDecimal.ZERO;
    private BigDecimal discountAmount = BigDecimal.ZERO;
    private BigDecimal paidAmount = BigDecimal.ZERO;
    private int skypointRedeemed = 0;
    private String cancelationPolicy = "";
    private List<RoomDetail> room;

}
