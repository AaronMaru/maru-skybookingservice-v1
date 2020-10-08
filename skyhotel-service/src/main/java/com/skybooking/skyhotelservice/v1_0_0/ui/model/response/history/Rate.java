package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.history;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
public class Rate {
    private BigInteger id;
    private Integer roomId;
    private String code;
    private String rateClass;
    private BigDecimal totalAmount;
    private BigDecimal sellingRate;
    private BigDecimal discount;
    private Boolean hotelMandatory;
    private String brand;
    private BigDecimal commissionPercentage;
    private BigDecimal commissionAmount;
    private BigDecimal commissionVat;
    private Boolean resident;
    private Boolean packaging;
    private String boardCode;
    private Integer totalRoom;
    private Integer totalAdult;
    private Integer totalChild;
    private String rateComment;
    private List<CancellationPolicy> cancellationPolicies = new ArrayList<>();
}
