package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.user;

import com.skybooking.core.validators.annotations.IsNumericString;
import com.skybooking.stakeholderservice.constant.UserContactMessageConstant;
import com.skybooking.stakeholderservice.exception.anotation.FieldMatch;
import com.skybooking.stakeholderservice.exception.anotation.IsPhoneCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "PASSWORD_NOT_MATCH")
public class UserContactRQ implements Serializable {

    @NotBlank(message = UserContactMessageConstant.PHONE_CODE_REQUIRED)
    @IsPhoneCode
    private String phoneCode;

    @NotBlank(message = UserContactMessageConstant.PHONE_NUMBER_REQUIRED)
    @Length(min = 8, max = 16, message = UserContactMessageConstant.PHONE_NUMBER_BETWEEN_8_16)
    @IsNumericString(message = UserContactMessageConstant.PHONE_NUMBER_INVALID)
    private String phoneNumber;

    @NotBlank(message = UserContactMessageConstant.EMAIL_REQUIRED)
    @Email(message = UserContactMessageConstant.EMAIL_INVALID)
    private String email;

    @Length(min = 6, max = 25, message = UserContactMessageConstant.PASSWORD_BETWEEN_6_25)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*?[#?!@$%^&*-]).+$", message = UserContactMessageConstant.PASSWORD_INVALID)
    private String password;
    private String confirmPassword;
}
