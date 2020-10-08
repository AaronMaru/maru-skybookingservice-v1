package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckRateHotel {
    private String checkIn;
    private String checkOut;
    private Integer code;
    @JsonIgnore
    private BigDecimal cost = BigDecimal.ZERO;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal previousTotalAmount = BigDecimal.ZERO;
    @JsonIgnore
    private BigDecimal markupPaymentAmount = BigDecimal.ZERO;
    @JsonIgnore
    private BigDecimal markupPaymentPercentage = BigDecimal.ZERO;
    @JsonIgnore
    private BigDecimal markupAmount = BigDecimal.ZERO;
    private Boolean isChanged = Boolean.FALSE;
    private String currencyCode;
    private List<Room> rooms;
}
