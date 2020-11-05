package com.skybooking.skypointservice.v1_0_0.client.event.model.requset;

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
