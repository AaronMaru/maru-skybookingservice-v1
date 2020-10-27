package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PaxRQ {

    @NotNull
    @Positive
    @Max(value = 16)
    private Integer age = 1;

}
