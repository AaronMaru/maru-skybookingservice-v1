package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.login;

import com.skybooking.stakeholderservice.exception.anotation.SocialForceNewPassword;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@SocialForceNewPassword
public class LoginSocialV110RQ extends LoginSocialRQ {

    @Size(min = 6, max = 25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*?[#?!@$%^&*-]).+$", message = "INVALID_PASSWORD")
    private String password;
    private String confirmPassword;

}
