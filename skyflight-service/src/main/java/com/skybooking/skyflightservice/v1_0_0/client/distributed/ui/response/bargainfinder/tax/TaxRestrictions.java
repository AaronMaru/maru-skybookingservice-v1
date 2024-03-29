package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.tax;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxRestrictions implements Serializable {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("maxAmount")
    private BigDecimal maxAmount;

    @JsonProperty("minAmount")
    private BigDecimal minAmount;

    @JsonProperty("rate")
    private BigDecimal rate;
}
