package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookingInfoRS {

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
    private Date bookingDate;
    private String statusKey;

}
