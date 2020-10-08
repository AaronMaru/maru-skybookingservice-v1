package com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkyOwnerLimitPointRQ {
    private Integer stakeholderUserId;
    private BigDecimal value;
}
