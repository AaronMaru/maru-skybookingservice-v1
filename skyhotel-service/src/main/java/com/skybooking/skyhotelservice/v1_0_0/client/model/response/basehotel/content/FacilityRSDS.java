package com.skybooking.skyhotelservice.v1_0_0.client.model.response.basehotel.content;

import lombok.Data;

import java.math.BigInteger;

@Data
public class FacilityRSDS {
    private Integer facilityCode;
    private Integer facilityGroupCode;
    private Description description;
    private Integer order;
    private BigInteger number;
    private Boolean voucher;
}

@Data
class Description {
    private String content;
}
