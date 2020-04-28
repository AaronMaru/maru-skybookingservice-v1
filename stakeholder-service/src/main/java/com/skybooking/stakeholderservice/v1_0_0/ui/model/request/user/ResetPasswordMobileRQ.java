package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.core.validators.annotations.IsNotEmpty;
import com.skybooking.core.validators.annotations.IsUsername;
import com.skybooking.stakeholderservice.exception.anotation.Code;
import com.skybooking.stakeholderservice.exception.anotation.FieldMatch;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.skybooking.core.validators.enumerations.UsernameAllowType.EMAIL;
import static com.skybooking.core.validators.enumerations.UsernameAllowType.PHONE_NUMBER;

@FieldMatch(first = "newPassword", second = "confirmNewPassword", message = "The password fields must match")
@Code(first = "username", second = "code", message = "Please provide country code")
@Data
public class ResetPasswordMobileRQ {

    @IsNotEmpty
    @IsUsername(allows = {PHONE_NUMBER, EMAIL})
    private String username;

    private String code;

    @IsNotEmpty
    @Size(min = 6, max = 25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*?[#?!@$%^&*-]).+$", message = "Password is required at least 8 characters with special characters, capitals and number.")
    private String newPassword;

    @IsNotEmpty
    private String confirmNewPassword;

}
