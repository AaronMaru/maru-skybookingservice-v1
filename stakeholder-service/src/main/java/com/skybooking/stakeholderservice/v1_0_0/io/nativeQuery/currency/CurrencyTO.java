package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.currency;

import lombok.Data;

@Data
public class CurrencyTO {

    private Integer currencyId;
    private String code;
    private String name;
    private String symbol;
    private Byte isTop;
    private Byte topOrder;

}
