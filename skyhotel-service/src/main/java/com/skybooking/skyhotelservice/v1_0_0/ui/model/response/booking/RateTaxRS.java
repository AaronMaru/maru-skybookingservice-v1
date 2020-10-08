package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking;

import lombok.Data;

@Data
public class RateTaxRS {
    private boolean included;
    private Number percent;
    private Number amount;
    private String currency;
    private String type;
    private Number clientAmount;
    private String clientCurrency;
}
