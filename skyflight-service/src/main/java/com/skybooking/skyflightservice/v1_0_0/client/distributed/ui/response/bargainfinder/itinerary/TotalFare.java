package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TotalFare implements Serializable {

    @JsonProperty("baseFareAmount")
    private BigDecimal baseFareAmount;

    @JsonProperty("bookingFeeAmount")
    private BigDecimal bookingFeeAmount;

    @JsonProperty("baseFareCurrency")
    private String baseFareCurrency;

    @JsonProperty("constructionAmount")
    private BigDecimal constructionAmount;

    @JsonProperty("airExtrasAmount")
    private BigDecimal airExtrasAmount;

    @JsonProperty("creditCardFeeAmount")
    private BigDecimal creditCardFeeAmount;

    @JsonProperty("constructionCurrency")
    private String constructionCurrency;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("effectivePriceDeviation")
    private BigDecimal effectivePriceDeviation;

    @JsonProperty("equivalentAmount")
    private BigDecimal equivalentAmount;

    @JsonProperty("equivalentCurrency")
    private String equivalentCurrency;

    @JsonProperty("serviceFeeAmount")
    private BigDecimal serviceFeeAmount;

    @JsonProperty("serviceFeeTax")
    private BigDecimal serviceFeeTax;

    @JsonProperty("totalPrice")
    private BigDecimal totalPrice;

    @JsonProperty("totalPriceWithAirExtras")
    private BigDecimal totalPriceWithAirExtras;

    @JsonProperty("totalTaxAmount")
    private BigDecimal totalTaxAmount;

    @JsonProperty("totalTypeObFee")
    private BigDecimal totalTypeObFee;
}
