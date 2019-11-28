package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.dashboard;

import lombok.Data;

import java.math.BigInteger;

@Data
public class BookingProgressRS {
    private BigInteger amount;
    private String status;
    private long percent;

    public BookingProgressRS(String status) {
        this.status = status;
        this.amount = BigInteger.valueOf(0);
        this.percent = 0;
    }

}
