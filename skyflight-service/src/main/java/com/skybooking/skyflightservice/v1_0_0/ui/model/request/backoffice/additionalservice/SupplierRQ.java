package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import com.skybooking.core.validators.annotations.IsIn;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SupplierRQ extends AdditionalItemRQ {

    @NotNull(message = "Supplier name is required")
    @IsIn(contains = {"SABRE", "PATHFINDER"}, caseSensitive = true)
    private String name;

    @NotNull(message = "Supplier fee is required")
    @Min(value = 0, message = "Supplier fee must be greater or equal 0")
    @Max(value = 1000000, message = "Supplier fee must be less than 1000000")
    private BigDecimal fee;

}
