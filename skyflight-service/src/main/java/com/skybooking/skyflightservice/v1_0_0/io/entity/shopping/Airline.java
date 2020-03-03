package com.skybooking.skyflightservice.v1_0_0.io.entity.shopping;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Airline implements Serializable {

    @Id
    private String code;
    private String name;
    private String operatingBy;
    private String url45;
    private String url90;
    private String currency;
    private BigDecimal price = BigDecimal.ZERO;
}
