package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger;

import com.skybooking.core.validators.annotations.*;
import com.skybooking.core.validators.groups.OnCreate;
import com.skybooking.core.validators.groups.OnUpdate;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PassengerRQ {

    @NotBlank(message = "Please provide a first name.", groups = {OnCreate.class})
    @Size(min = 1, max = 35, message = "The first name must be between 1 to 35 characters.", groups = {OnCreate.class, OnUpdate.class})
    @IsAlpha
    private String firstName;

    @NotBlank(message = "Please provide a last name.", groups = {OnCreate.class})
    @Size(min = 1, max = 35, message = "The last name must be between 1 to 35 characters.", groups = {OnCreate.class, OnUpdate.class})
    @IsAlpha
    private String lastName;

    @NotBlank(message = "Please provide a gender.", groups = {OnCreate.class})
    @IsIn(contains = {"MALE", "FEMALE"}, caseSensitive = true)
    private String gender;

    @NotNull(message = "Please provide a birth date.", groups = {OnCreate.class})
    @IsDate
    @IsPastDate
    private String birthDate;

    @NotBlank(message = "Please provide a nationality.", groups = {OnCreate.class})
    @Size(min = 1, max = 35, message = "The nationality must be not blank value.", groups = {OnCreate.class, OnUpdate.class})
    private String nationality;

    @NotBlank(message = "Please provide an identity card number.", groups = {OnCreate.class})
    @IsAlphanumeric
    @Size(min = 1, max = 15, message = "The identifier number must be not blank value.", groups = {OnCreate.class, OnUpdate.class})
    private String idNumber;

    @IsNotEmpty
    @IsDate
    @IsFutureDate
    private String expireDate;

    @NotNull(message = "Please provide a type of the identity card.", groups = {OnCreate.class})
    @Min(value = 0, message = "ID type is valid in 0 or 1.", groups = {OnCreate.class, OnUpdate.class})
    @Max(value = 1, message = "ID type is valid in 0 or 1.", groups = {OnCreate.class, OnUpdate.class})
    private Integer idType;

}
