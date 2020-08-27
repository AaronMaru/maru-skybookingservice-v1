package com.skybooking.eventservice.v1_0_0.ui.model.request.email;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkyPointEarnedRQ {

    private BigDecimal amount;
    private String transactionCode;
    private String email;
    private String fullName;

}