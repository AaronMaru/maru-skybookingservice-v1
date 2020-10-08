package com.skybooking.skypointservice.v1_0_0.ui.model.request.refund;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RefundRQ {
    private BigDecimal amount;
    private String userCode;
    private String createdBy;
    private Integer stakeholderUserId;
    private Integer stakeholderCompanyId;
    private String name;
    private String email;
    private String remark;

    private String referenceCode;

    @IsNotEmpty
    @IsIn(contains = {"REFUNDED_FLIGHT", "REFUNDED_HOTEL"})
    private String transactionFor;

}
