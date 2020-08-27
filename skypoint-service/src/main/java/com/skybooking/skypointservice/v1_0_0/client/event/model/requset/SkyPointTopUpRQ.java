package com.skybooking.skypointservice.v1_0_0.client.event.model.requset;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SkyPointTopUpRQ {

    private BigDecimal amount;
    private String email;
    private String fullName;
    private BigDecimal earnAmount;
    private String phone;
    private String transactionId;
    private String transactionDate;
}
