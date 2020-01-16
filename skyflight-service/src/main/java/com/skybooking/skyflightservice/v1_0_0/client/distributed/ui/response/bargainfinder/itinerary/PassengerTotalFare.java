package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.itinerary;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassengerTotalFare implements Serializable {

    @JsonProperty("baseFareAmount")
    private BigDecimal baseFareAmount;

    @JsonProperty("baseFareCurrency")
    private String baseFareCurrency;

    @JsonProperty("bookingFeeAmount")
    private BigDecimal bookingFeeAmount;

    @JsonProperty("cat35CommissionAmount")
    private BigDecimal cat35CommissionAmount;

    @JsonProperty("cat35CommissionPercentage")
    private BigDecimal cat35CommissionPercentage;

    @JsonProperty("cat35MarkupAmount")
    private BigDecimal cat35MarkupAmount;

    @JsonProperty("commissionAmount")
    private BigDecimal commissionAmount;

    @JsonProperty("commissionAmountInEquivalent")
    private BigDecimal commissionAmountInEquivalent;

    @JsonProperty("commissionPercentage")
    private BigDecimal commissionPercentage;

    @JsonProperty("commissionSource")
    private String commissionSource;

    @JsonProperty("constructionAmount")
    private BigDecimal constructionAmount;

    @JsonProperty("constructionCurrency")
    private String constructionCurrency;

    @JsonProperty("creditCardFeeAmount")
    private BigDecimal creditCardFeeAmount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("effectiveDeviationType")
    private String effectiveDeviationType;

    @JsonProperty("effectivePriceDeviation")
    private BigDecimal effectivePriceDeviation;

    @JsonProperty("equivalentAmount")
    private BigDecimal equivalentAmount;

    @JsonProperty("equivalentCurrency")
    private String equivalentCurrency;

    @JsonProperty("exchangeRateOne")
    private BigDecimal exchangeRateOne;

    @JsonProperty("noMarkupBaseFareAmount")
    private BigDecimal noMarkupBaseFareAmount;

    @JsonProperty("stopoverChargeAmount")
    private BigDecimal stopoverChargeAmount;

    @JsonProperty("totalFare")
    private BigDecimal totalFare;

    @JsonProperty("totalTaxAmount")
    private BigDecimal totalTaxAmount;

    @JsonProperty("totalTtypeObFee")
    private BigDecimal totalTtypeObFee;
}
