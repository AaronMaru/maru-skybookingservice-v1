package com.skybooking.skypointservice.v1_0_0.ui.model.request.limitPoint;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AvailablePointRQ {
    @NotNull(message = "stakeholderUser is missing")
    @Min(value = 1)
    private Integer stakeholderUserId;
}
