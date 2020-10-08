package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.availability.room;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateSupplementRSDS {
    private String code;
    private String name;
    private String from;
    private String to;
    private BigDecimal amount = BigDecimal.ZERO;
    private Number nights;
    private Number paxNumber;
}
