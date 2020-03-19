package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BookingEmailDetailRS {

    private Integer bookingId;
    private String bookingCode;
    private String transId;
    private String payMethod;
    private String pnrCode;
    private String tripType;
    private Integer skyuserId;
    private String skyuserName;
    private String skyuserPhoto;
    private BigDecimal adult;
    private BigDecimal child;
    private BigDecimal infant;
    private BigDecimal totalPass;
    private String cabinName;
    private String cabinCode;
    private BigDecimal totalAmount;
    private BigDecimal markupAmount;
    private BigDecimal markupPayAmount;
    private BigDecimal disPayment;
    private Date bookDate;
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

    List<BookingOdRS> bookingOd = new ArrayList<>();
    List<BookingTicketRS> airTickets = new ArrayList<>();
    List<BookingAirItinPriceRS> airItinPrices = new ArrayList<>();

}
