package com.skybooking.skyflightservice.v1_0_0.ui.model.response.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentMandatoryRS {

    private BigDecimal amount;
    private String Description;
    private String name;
    private String email;
    private String phoneNumber;
    private Integer skyuserId;
    private Integer companyId;

}
