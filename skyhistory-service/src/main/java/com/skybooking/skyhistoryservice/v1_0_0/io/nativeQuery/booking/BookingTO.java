package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookingTO {

    private Integer bookingId;
    private String bookingCode;
    private String transId;
    private String payMethod;
    private String pnrCode;
    private String tripType;
    private Integer skyuserId;
    private String skyuserName;
    private String skyuserPhoto;
    private BigDecimal adult = BigDecimal.ZERO;
    private BigDecimal child = BigDecimal.ZERO;
    private BigDecimal infant = BigDecimal.ZERO;
    private BigDecimal totalPass = BigDecimal.ZERO;
    private String cabinName;
    private String cabinCode;
    private BigDecimal totalAmount;
    private BigDecimal markupAmount;
    private BigDecimal markupPayAmount;
    private Date bookDate;
    private Date depDate;
    private String currencyCode;
    private String contName;
    private String itineraryFile;
    private String itineraryPath;
    private String recieptFile;
    private String recieptPath;
    private String bookType;
    private Byte status;
    private String statusKey;
    private String contLocationCode;
    private String contPhone;
    private String contPhoneCode;
    private String contEmail;

}
