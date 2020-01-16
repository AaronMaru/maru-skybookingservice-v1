package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrencyConversion implements Serializable {

    @JsonProperty("from")
    private String from;

    @JsonProperty("to")
    private String to;

    @JsonProperty("exchangeRate")
    private BigDecimal exchangeRate;

    @JsonProperty("exchangeRateUsed")
    private BigDecimal exchangeRateUsed;

    @JsonProperty("overriden")
    private Boolean overridden;
}
