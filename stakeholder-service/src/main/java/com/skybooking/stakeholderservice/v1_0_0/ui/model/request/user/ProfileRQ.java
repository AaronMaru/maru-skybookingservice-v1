package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.core.validators.annotations.*;
import lombok.Data;

@Data
public class ProfileRQ {

    @IsNotEmpty
    @IsNotContainSpecialSymbol
    private String firstName;

    @IsNotEmpty
    @IsNotContainSpecialSymbol
    private String lastName;

    @IsNotEmpty
    private String nationality;

    @IsIn(contains = {"MALE", "FEMALE"}, caseSensitive = true)
    private String gender;

    @IsNotEmpty
    private String address;

    @IsDate
    @Ages(min = 7, max = 120)
    private String dateOfBirth;

}
