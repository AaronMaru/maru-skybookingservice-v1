package com.skybooking.skyhistoryservice.v1_0_0.ui.model.response.booking.detail;

import lombok.Data;

@Data
public class PriceBreakdownRS {

    private String passType;
    private Integer passQty;
    private Double baseFare;
    private Double totalTax;
    private Double totalAmount;
    private String currencyCode;
    private String nonRefund;

}
