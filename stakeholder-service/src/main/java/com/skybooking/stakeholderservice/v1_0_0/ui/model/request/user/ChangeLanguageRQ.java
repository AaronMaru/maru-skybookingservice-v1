package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.core.validators.annotations.IsNotEmpty;
import javax.validation.constraints.NotNull;

public class ChangeLanguageRQ {

    @NotNull(message = "Please provide a device id")
    @IsNotEmpty
    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
