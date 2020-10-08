package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary;

import com.skybooking.core.validators.annotations.IsNumericString;
import com.skybooking.skyflightservice.exception.anotation.IsPhoneCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ContactPerson {

    @NotBlank(message = "REQUIRE_NAME")
    private String name;

    @NotBlank(message = "REQUIRE_EMAIL")
    @Email(message = "EMAIL_INVALID")
    private String email;

    @NotBlank(message = "REQUIRE_PHONE_CODE")
    @Length(min = 2, max = 6, message = "PHONE_CODE_INVALID")
    @IsPhoneCode(message = "PHONE_CODE_INVALID")
    private String phoneCode;

    @NotBlank(message = "REQUIRE_PHONE_NUMBER")
    @Length(min = 8, max = 16, message = "PHONE_NUMBER_INVALID")
    @IsNumericString(message = "PHONE_NUMBER_INVALID")
    private String phoneNumber;

}
