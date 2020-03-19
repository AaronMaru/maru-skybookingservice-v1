package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class BookingProgressRS {
    private BigInteger amount;
    private String statusKey;
    private Float percentage;

    public BookingProgressRS(String status) {
        this.statusKey = status;
        this.amount = BigInteger.valueOf(0);
        this.percentage = Float.valueOf(0);
    }

}
