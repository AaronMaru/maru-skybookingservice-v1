package com.skybooking.eventservice.v1_0_0.ui.model.request.notification;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class NotificationRQ {

    @IsNotEmpty
    private String transactionCode;

    @IsNotEmpty
    @IsIn(contains = {"EARNED_POINT", "REDEEM_POINT", "REFUND_POINT"})
    private String type;

    private String script;

}
