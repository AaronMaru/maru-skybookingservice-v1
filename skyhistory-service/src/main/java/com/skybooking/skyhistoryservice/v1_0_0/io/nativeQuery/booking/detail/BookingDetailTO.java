package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookingDetailTO {

    private Integer bookingId;
    private String bookingCode;
    private String transactionCode;
    private Date bookingDate;
    private String bookingType;
    private String pnrCode;
    private String tripType;
    private BigDecimal adult = BigDecimal.ZERO;
    private BigDecimal child = BigDecimal.ZERO;
    private BigDecimal infant = BigDecimal.ZERO;
    private BigDecimal totalPass = BigDecimal.ZERO;
    private String cabinName;
    private String cabinCode;
    private String currencyCode;
    private String urlItinerary;
    private String urlReceipt;
    private String bookingByUsername;
    private String bookingByUserPhoto;

    private BigDecimal totalAmount;
    private BigDecimal markupAmount;
    private BigDecimal markupPayAmount;
    private BigDecimal discountAmount;
    private BigDecimal paidAmount;

    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String statusKey;

    private String holderName;
    private String bankName;
    private String cardType;
    private String cardNumber;
    private String paymentType;

}
