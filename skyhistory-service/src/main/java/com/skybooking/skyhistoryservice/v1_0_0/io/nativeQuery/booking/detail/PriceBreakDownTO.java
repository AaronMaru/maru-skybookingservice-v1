package com.skybooking.skyhistoryservice.v1_0_0.io.nativeQuery.booking.detail;

import lombok.Data;

@Data
public class PriceBreakDownTO {

    private String passType;
    private Integer passQty;
    private Double baseFare;
    private Double totalTax;
    private Double totalAmount;
    private String currencyCode;
    private Boolean nonRefund;

}
