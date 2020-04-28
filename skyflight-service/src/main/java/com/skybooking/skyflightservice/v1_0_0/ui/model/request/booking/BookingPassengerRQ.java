package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import com.skybooking.core.validators.annotations.IsAlpha;
import com.skybooking.core.validators.annotations.IsAlphanumeric;
import com.skybooking.core.validators.annotations.IsIn;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;

@Data
public class BookingPassengerRQ {

    @NotBlank(message = "Please provide a first name.")
    @Size(min = 1, max = 35, message = "The first name must be between 1 to 35 characters.")
    @IsAlpha
    private String firstName;

    @NotBlank(message = "Please provide a last name.")
    @Size(min = 1, max = 35, message = "The first name must be between 1 to 35 characters.")
    @IsAlpha
    private String lastName;

    @NotBlank(message = "Please provide a gender.")
    @IsIn(contains = {"MALE", "FEMALE"}, caseSensitive = true)
    private String gender;

    @NotNull(message = "Please provide a birth date.")
    @Past(message = "Passenger's birth date must be in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NotBlank(message = "Please provide a nationality.")
    @Size(min = 1, max = 35, message = "The nationality must be not blank value.")
    private String nationality;

    @NotNull(message = "Please provide a type of the identity card.")
    @Min(value = 0, message = "ID type is valid in 0 or 1.")
    @Max(value = 1, message = "ID type is valid in 0 or 1.")
    private Integer idType;

    @NotBlank(message = "Please provide an identity card number.")
    @IsAlphanumeric
    @Size(min = 1, max = 15, message = "The identifier number must be not blank value.")
    private String idNumber;

    @NotNull(message = "Passport's expire dat is required.")
    @Future(message = "Passport's expire date must be in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date expireDate;

}
