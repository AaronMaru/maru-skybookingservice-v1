package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.currency;

import lombok.Data;

@Data
public class CurrencyRS {

    private Integer currencyId;
    private String code;
    private String name;
    private String symbol;
    private Byte isTop;
    private Byte topOrder;

}
