package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate;

import com.skybooking.skyhotelservice.constant.CurrencyConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount implements Serializable {
    private String name;
    private String code;
    private BigDecimal amount = BigDecimal.ZERO;
    private String currencyCode = CurrencyConstant.USD.CODE;

}

