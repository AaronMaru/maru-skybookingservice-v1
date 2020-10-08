package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
public class HistoryListRS {
    private BigInteger id;
    private String bookingCode;
    private String referenceCode;
    private String cancellationReference;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date bookingDate;
    private Integer hotelCode;
    private String hotelCategoryCode;
    private String hotelDestinationCode;
    private Integer hotelZoneCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date checkIn;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date checkOut;
    private Boolean isCancellation;
    private Boolean isModification;
    private BigDecimal cancellationAmount;
    private BigDecimal totalAmount;
    private String currencyCode;
    private String status;
    private List<Room> rooms = new ArrayList<>();
}
