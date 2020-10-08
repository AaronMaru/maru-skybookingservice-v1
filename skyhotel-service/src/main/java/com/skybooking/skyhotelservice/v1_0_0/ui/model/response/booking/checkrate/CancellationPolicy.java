package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CancellationPolicy implements Serializable {
    private BigDecimal net;
    private BigDecimal amount;
    private String from;
    private String currency;
}
