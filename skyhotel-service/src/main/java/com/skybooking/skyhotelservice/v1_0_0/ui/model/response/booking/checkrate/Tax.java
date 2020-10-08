package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tax implements Serializable {
    private String type;
    private BigDecimal amount = BigDecimal.ZERO;
    private String currency;

    public Tax(String type, String currency, BigDecimal amount) {
    }

}
