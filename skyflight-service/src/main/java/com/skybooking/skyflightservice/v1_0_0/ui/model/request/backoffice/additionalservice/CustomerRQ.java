package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CustomerRQ extends AdditionalItemRQ {

    @NotNull(message = "Customer fee is required")
    @Min(value = 0, message = "Customer fee must be greater or equal 0")
    @Max(value = 1000000, message = "Customer fee must be less than 1000000")
    private BigDecimal fee;

}
