package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BookingDetailRS {

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
    private BigDecimal totalAmount;
    private BigDecimal disPayment;
    private Date bookDate;
    private String currencyCode;
    private String contName;
    private Byte status;
    private String statusKey;

    List<BookingOdRS> bookingOd = new ArrayList<>();
    List<BookingTicketRS> airTickets = new ArrayList<>();
    List<BookingAirItinPriceRS> airItinPrices = new ArrayList<>();

}
