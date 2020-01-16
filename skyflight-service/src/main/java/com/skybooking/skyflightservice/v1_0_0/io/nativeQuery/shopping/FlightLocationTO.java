package com.skybooking.skyflightservice.v1_0_0.io.nativeQuery.shopping;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FlightLocationTO implements Serializable {

    private String code;
    private String airport;
    private String city;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer ccId;

}
