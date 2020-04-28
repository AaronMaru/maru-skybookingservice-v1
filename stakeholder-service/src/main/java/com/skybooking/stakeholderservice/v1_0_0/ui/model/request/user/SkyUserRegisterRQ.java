package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.core.validators.annotations.*;
import com.skybooking.stakeholderservice.exception.anotation.*;
import lombok.Data;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.skybooking.core.validators.enumerations.UsernameAllowType.*;


@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
@Code(first = "username", second = "code", message = "Please provide country code")
@UsernameUnique(first = "username", second = "code", message = "Username already exist")
@Data
public class SkyUserRegisterRQ {

    @IsNotEmpty
    @IsUsername(allows = {PHONE_NUMBER, EMAIL})
    private String username;

    @IsNotEmpty
    @IsNotContainSpecialSymbol
    private String firstName;

    @IsNotEmpty
    @IsNotContainSpecialSymbol
    private String lastName;

    private String code;

    @IsNotEmpty
    @Size(min = 6, max = 25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*?[#?!@$%^&*-]).+$", message = "Invalid password")
    private String password;

    @IsNotEmpty
    private String confirmPassword;

    @IsNotEmpty
    @IsIn(contains = {"personal", "bussiness"})
    private String typeSky;

}
