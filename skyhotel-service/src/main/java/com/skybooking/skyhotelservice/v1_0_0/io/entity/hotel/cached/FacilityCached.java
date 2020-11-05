package com.skybooking.skyhotelservice.v1_0_0.io.entity.hotel.cached;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class FacilityCached implements Serializable {
    private Integer code;
    private String key;
    private String name;
    private String icon;
    private Integer groupCode;
    private String groupKey;
    private String groupName;

    private Boolean isPolicy;
    private Boolean isPopular;

    private Integer ageFrom;
    private Integer ageTo;
    private String dateFrom;
    private String dateTo;
    private String timeFrom;
    private String timeTo;
    private BigDecimal amount;
    private String currency;
    private Integer distance;
    private Boolean indFee;
    private Boolean indLogic;
    private Boolean indYesOrNo;
    private Boolean voucher;
    private Integer numbers;
    private Integer orders;
    private Integer typologyCode;
    private Boolean ageFromFlag;
    private Boolean ageToFlag;
    private Boolean amountFlag;
    private Boolean appTypeFlag;
    private Boolean currencyFlag;
    private Boolean dateFromFlag;
    private Boolean dateToFlag;
    private Boolean distanceFlag;
    private Boolean feeFlag;
    private Boolean indYesOrNoFlag;
    private Boolean logicFlag;
    private Boolean textFlag;
    private Boolean timeFromFlag;
    private Boolean timeToFlag;
    private Boolean numberFlag;
}
