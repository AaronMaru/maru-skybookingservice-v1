package com.skybooking.skyhistoryservice.v1_0_0.transformer.mail;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingInfoTF {

    private Integer bookingId;
    private String bookingCode;
    private String transactionCode;
    private String pnrCode;
    private String tripType;
    private BigDecimal adult;
    private BigDecimal child;
    private BigDecimal infant;
    private BigDecimal totalPass;
    private String cabinName;
    private String cabinCode;
    private String urlItinerary;
    private String urlReceipt;
    private String bookingByUsername;
    private String bookingByUserPhoto;
    private String bookingDate;
    private String statusKey;

}
