package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Amount implements Serializable {

    private String currency;
    private String currencyCode;
    private BigDecimal content;

}
