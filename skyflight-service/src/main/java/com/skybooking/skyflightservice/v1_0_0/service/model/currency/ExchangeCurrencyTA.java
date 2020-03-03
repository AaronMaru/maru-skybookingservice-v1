package com.skybooking.skyflightservice.v1_0_0.service.model.currency;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeCurrencyTA {

    private String from;
    private double fromRate;
    private String to;
    private double toRate;
    private double amount;

}
