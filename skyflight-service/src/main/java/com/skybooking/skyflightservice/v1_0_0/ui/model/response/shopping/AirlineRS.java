package com.skybooking.skyflightservice.v1_0_0.ui.model.response.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AirlineRS implements Serializable {

    private String code;
    private String name;
    private String operatingBy;
    private String url45;
    private String url90;
    private String currency;
    private BigDecimal price = BigDecimal.ZERO;

}
