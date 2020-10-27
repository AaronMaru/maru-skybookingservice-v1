package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.core.validators.annotations.IsNotContainSpecialSymbol;
import com.skybooking.core.validators.annotations.IsNotEmpty;
import com.skybooking.core.validators.annotations.IsUsername;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.skybooking.core.validators.enumerations.UsernameAllowType.EMAIL;
import static com.skybooking.core.validators.enumerations.UsernameAllowType.PHONE_NUMBER;

@Data
public class LoginSocialRQ {

    @NotNull(message = "Please provide a username")
    @IsNotEmpty
    @IsUsername(allows = {PHONE_NUMBER, EMAIL})
    private String username;

    @IsNotEmpty
    private String providerId;

    @IsNotEmpty
    @IsIn(contains = {"facebook", "google", "apple"})
    private String provider;

    @IsNotEmpty
    @IsNotContainSpecialSymbol
    private String firstName;

    @IsNotEmpty
    @IsNotContainSpecialSymbol
    private String lastName;

    private String photoUrl;
    private String typeSky;
    private String password;
}
