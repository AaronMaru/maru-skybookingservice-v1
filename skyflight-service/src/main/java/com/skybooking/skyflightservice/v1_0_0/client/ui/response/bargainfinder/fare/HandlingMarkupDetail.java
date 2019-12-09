package com.skybooking.skyflightservice.v1_0_0.client.ui.response.bargainfinder.fare;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HandlingMarkupDetail {

    @JsonProperty("amountCurrency")
    private String amountCurrency;

    @JsonProperty("fareAmountAfterMarkup")
    private BigDecimal fareAmountAfterMarkup;

    @JsonProperty("markupAmount")
    private BigDecimal markupAmount;

    @JsonProperty("markupHandlingFeeAppId")
    private String markupHandlingFeeAppId;

    @JsonProperty("markupRuleItemNumber")
    private Integer markupRuleItemNumber;

    @JsonProperty("markupRuleSourcePcc")
    private String markupRuleSourcePcc;

    @JsonProperty("markupTypeCode")
    private String markupTypeCode;

    @JsonProperty("retailerRuleQualifier")
    private String retailerRuleQualifier;

}
