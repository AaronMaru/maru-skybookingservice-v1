package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.setting;

import com.skybooking.core.validators.annotations.IsIn;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SettingNotificationRQ {

    @NotEmpty(message = "The setting can not be empty string.")
    @IsIn(contains = {"email_notification", "push_notification"}, caseSensitive = true)
    private String setting;

    @NotNull(message = "The setting enabled can not be null.")
    private Boolean enabled;

}
