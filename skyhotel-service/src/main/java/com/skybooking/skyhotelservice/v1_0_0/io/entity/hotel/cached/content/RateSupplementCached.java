package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached.content;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RateSupplementCached implements Serializable {
    private String code;
    private String name;
    private String from;
    private String to;
    private BigDecimal amount = BigDecimal.ZERO;
    private Number nights;
    private Number paxNumber;
}
