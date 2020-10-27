package com.skybooking.skypointservice.v1_0_0.ui.model.request;

import com.skybooking.core.validators.annotations.IsEmail;
import com.skybooking.core.validators.annotations.IsPhoneNumber;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ContactInfo {
    @NotNull(message = "key_email_not_null")
    @IsEmail(message = "email_not_valid")
    private String email;

    @NotNull(message = "key_name_not_null")
    private String name;

    @NotNull(message = "key_phoneCode_not_null")
    private String phoneCode;

    @NotNull(message = "key_phoneNumber_not_null")
    @IsPhoneNumber(message = "phone_number_not_valid")
    private String phoneNumber;
}
