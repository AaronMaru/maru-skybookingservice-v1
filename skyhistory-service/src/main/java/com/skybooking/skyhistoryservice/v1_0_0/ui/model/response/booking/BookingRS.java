package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class BookingRS {

    private Integer bookingId;
    private String bookingCode;
    private String pnrCode;
    private String tripType;
    private String staffName;
    private BigDecimal adult;
    private BigDecimal child;
    private BigDecimal infant;
    private BigDecimal totalPass;
    private String cabinName;
    private String cabinCode;
    private BigDecimal totalAmount;
    private BigDecimal disPayment;
    private Date bookDate;
    private String currencyCode;
    private String contName;
    private Byte status;
    private String statusKey;
    private String contEmail;
    private String contPhone;
    private String contPhoneCode;

    private List<BookingOdRS> bookingOd;


}

