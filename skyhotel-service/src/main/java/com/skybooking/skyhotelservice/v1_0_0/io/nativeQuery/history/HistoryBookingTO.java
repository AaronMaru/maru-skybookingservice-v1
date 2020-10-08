package com.skybooking.skyhotelservice.v1_0_0.io.nativeQuery.history;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HistoryBookingTO {

    private Integer stakeholderUserId;
    private Integer stakeholderCompanyId;
    private String bookingCode;
    private String bookingReference;
    private String referenceCode;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String status;
    private BigDecimal cost;
    private BigDecimal totalAmount;
    private BigDecimal totalAmountBeforeDiscount;
    private BigDecimal markupAmount;
    private BigDecimal paymentFeeAmount;
    private BigDecimal markupPayAmount;
    private BigDecimal discountPaymentAmount;
    private BigDecimal commissionAmount;
    private BigDecimal refundFeeAmount;
    private String currencyCode;
    private Date checkIn;
    private Date checkOut;
    private String cardHolderName;
    private String cardType;
    private String cardNumber;
    private BigDecimal paidAmount;
    private String paymentId;
    private String paymentOf;

}