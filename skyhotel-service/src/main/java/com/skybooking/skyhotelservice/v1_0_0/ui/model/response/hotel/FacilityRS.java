package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class FacilityRS {
    private Integer code;
    private String key;
    private String name;
    private String icon;
    private Integer groupCode;
    private String groupName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isPolicy = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isPopular = false;

    private Integer ageFrom;
    private Integer ageTo;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private BigDecimal amount;
    private String currency;
    private Integer distance;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean indFee = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean indLogic = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean indYesOrNo = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean voucher = false;

    private Integer numbers;
    private Integer orders;
    private Integer typologyCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean ageFromFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean ageToFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean amountFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean appTypeFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean currencyFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean dateFromFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean dateToFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean distanceFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean feeFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean indYesOrNoFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean logicFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean textFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean timeFromFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean timeToFlag = false;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean numberFlag = false;

    public String getKey() {
        return this.groupCode + ":" + this.code;
    }
}
