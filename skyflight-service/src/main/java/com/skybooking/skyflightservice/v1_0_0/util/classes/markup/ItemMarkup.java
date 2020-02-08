package com.skybooking.skyflightservice.v1_0_0.util.classes.markup;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemMarkup {

    private BigDecimal markupAmount;
    private BigDecimal paymentMethodMarkupAmount;

    public ItemMarkup() {
    }

    public ItemMarkup(BigDecimal markupAmount, BigDecimal paymentMethodMarkupAmount) {
        this.markupAmount = markupAmount;
        this.paymentMethodMarkupAmount = paymentMethodMarkupAmount;
    }
}
