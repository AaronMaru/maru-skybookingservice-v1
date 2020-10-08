package com.skybooking.eventservice.v1_0_0.ui.model.request.notification;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class NotificationTopUpRQ {

    @Min(1)
    @NotNull
    private Long stakeholderUserId;

    @Min(1)
    @NotNull
    private Long stakeholderCompanyId;

    @IsNotEmpty
    private String transactionCode;

    @IsNotEmpty
    @IsIn(contains = {"TOP_UP_POINT"})
    private String type;

    private String script;

}
