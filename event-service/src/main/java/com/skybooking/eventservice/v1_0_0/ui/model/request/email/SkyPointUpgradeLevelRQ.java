package com.skybooking.eventservice.v1_0_0.ui.model.request.email;

import com.skybooking.core.validators.annotations.IsEmail;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SkyPointUpgradeLevelRQ {

    @IsEmail
    private String email;

    @NotNull
    private String fullName;

    @NotNull
    private String oldLevel;

    @NotNull
    private String newLevel;


}
