package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.response.bargainfinder.tax;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tax implements Serializable {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("country")
    private String country;

    @JsonProperty("station")
    private String station;

    @JsonProperty("code")
    private String code;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("description")
    private String description;

    @JsonProperty("publishedAmount")
    private BigDecimal publishedAmount;

    @JsonProperty("publishedCurrency")
    private String publishedCurrency;

    @JsonProperty("taxReissue")
    private TaxReissue taxReissue;

    @JsonProperty("taxRestrictions")
    private TaxRestrictions taxRestrictions;

}
