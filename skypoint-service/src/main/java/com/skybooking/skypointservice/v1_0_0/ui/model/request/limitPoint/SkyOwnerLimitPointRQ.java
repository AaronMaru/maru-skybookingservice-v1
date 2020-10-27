package com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SkyOwnerLimitPointRQ {

    @NotNull(message = "stakeholderUserId_not_null")
    @Min(value = 1, message = "stakeholder_not_valid")
    private Integer stakeholderUserId;

    @NotNull(message = "value_not_null")
    @Min(value = 1, message = "value_not_valid")
    private BigDecimal value;
}
