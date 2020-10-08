package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class HistoryBookingRS {

    private String bookingCode;
    @JsonIgnore
    private String bookingReference;
    private String contactName;
    @JsonIgnore
    private BigDecimal totalAmount = BigDecimal.ZERO;

    private BigDecimal totalAmountBeforeDiscount = BigDecimal.ZERO;
    private BigDecimal unitAmount = BigDecimal.ZERO;
    private String currencyCode;
    private String status;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    private String checkIn;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    private String checkOut;

    private String hotelName;
    private String hotelLocation;
    @JsonFormat(pattern="yyyy-MM-dd yyyy-MM-dd'T'HH:mm:ssXXX")
    private String bookingDate;
    private Long nights;
    private Integer totalRoom = 0;
    private Integer totalAdult = 0;
    private String thumbnail;

}
