package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.core.validators.annotations.IsNotEmpty;
import com.skybooking.core.validators.annotations.IsUsername;
import com.skybooking.stakeholderservice.exception.anotation.*;
import lombok.Data;

import static com.skybooking.core.validators.enumerations.UsernameAllowType.EMAIL;
import static com.skybooking.core.validators.enumerations.UsernameAllowType.PHONE_NUMBER;

@Code(first = "username", second = "code", message = "Please provide country code")
@UsernameUnique(first = "username", second = "code", message = "Username already exists")
@Data
public class UpdateContactRQ {

    @IsNotEmpty
    @IsUsername(allows = {PHONE_NUMBER, EMAIL})
    private String username;

    private String password;

    private String code;

    private String token;

}
