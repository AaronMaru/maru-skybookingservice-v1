package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.tax;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxReissue implements Serializable {

    @JsonProperty("taxType")
    private String taxType;

    @JsonProperty("maxAmount")
    private BigDecimal maxAmount;

    @JsonProperty("maxCurrency")
    private String maxCurrency;

    @JsonProperty("refundable")
    private Boolean refundable;

    @JsonProperty("restrictionApply")
    private Boolean restrictionApply;
}
