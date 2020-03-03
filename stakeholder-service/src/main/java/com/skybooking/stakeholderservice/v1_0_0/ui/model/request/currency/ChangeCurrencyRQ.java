package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.currency;

import javax.validation.constraints.NotNull;

public class ChangeCurrencyRQ {

    @NotNull(message = "Please provide a currency id")
    private Long currencyId;

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }
}
