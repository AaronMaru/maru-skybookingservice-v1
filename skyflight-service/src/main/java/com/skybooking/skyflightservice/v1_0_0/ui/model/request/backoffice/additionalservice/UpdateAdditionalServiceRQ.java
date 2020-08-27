package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateAdditionalServiceRQ {

    @NotNull(message = "Employee ID is required")
    @Min(value = 1, message = "Employee ID must be greeter than 0")
    private Integer employeeId;

    @Valid
    private ServiceRQ service;

    @Valid
    private SupplierRQ supplier;

    @Valid
    private CustomerRQ customer;

    @Valid
    private BankRQ bank;

}
