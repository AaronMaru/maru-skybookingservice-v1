package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CustomerRQ extends AdditionalItemRQ {

    @NotNull(message = "REQUIRE_CUSTOMER_FEE")
    @Min(value = 0, message = "CUSTOMER_FEE_MIN_0")
    @Max(value = 1000000, message = "CUSTOMER_FEE_MAX_1000000")
    private BigDecimal fee;

}
