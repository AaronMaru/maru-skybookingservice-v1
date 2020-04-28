package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import com.skybooking.core.validators.annotations.IsNumericString;
import com.skybooking.skyflightservice.exception.anotation.IsPhoneCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class BookingContactRQ {

    @NotBlank(message = "Contact person's name is required.")
    private String name;

    @NotBlank(message = "Contact person's email address is required.")
    @Email
    private String email;

    @NotBlank(message = "Contact person's phone code is required.")
    @Length(min = 2, max = 6, message = "Phone code is between 2 and 6 characters.")
    @IsPhoneCode
    private String phoneCode;

    @NotBlank(message = "Contact person's phone number is required.")
    @Length(min = 8, max = 16, message = "Phone number is between 8 and 16 digits.")
    @IsNumericString
    private String phoneNumber;

}
