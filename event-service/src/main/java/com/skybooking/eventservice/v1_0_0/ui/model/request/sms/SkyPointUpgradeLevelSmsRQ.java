package com.skybooking.eventservice.v1_0_0.ui.model.request.sms;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class SkyPointUpgradeLevelSmsRQ {

    @NotNull(message = "Phone is missing.")
    private String phone;

    @NotNull
    private String level;

}
