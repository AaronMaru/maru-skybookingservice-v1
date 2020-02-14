package com.skybooking.staffservice.v1_0_0.ui.model.response.staff;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by : maru
 * Date  : 2/6/2020
 * Time  : 2:04 PM
 */

public class AmountRS {

    private BigInteger quantity = BigInteger.valueOf(0);
    private BigDecimal amount = BigDecimal.valueOf(0.0);

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
