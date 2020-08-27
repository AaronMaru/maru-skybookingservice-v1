package com.skybooking.skyflightservice.v1_0_0.service.model.booking.reservation;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Base implements Serializable {

    private String currencyCode;
    private String currency;
    private BigDecimal content;

}
