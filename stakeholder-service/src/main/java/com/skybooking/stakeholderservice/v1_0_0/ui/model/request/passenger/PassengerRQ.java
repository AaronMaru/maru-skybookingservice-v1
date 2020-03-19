package com.skybooking.stakeholderservice.v1_0_0.ui.model.request.passenger;

import com.skybooking.stakeholderservice.exception.anotation.Include;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.interfaces.OnCreate;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.request.interfaces.OnUpdate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PassengerRQ {

    @NotBlank(message = "Please provide a first name.", groups = {OnCreate.class})
    @Size(min = 1, max = 35, message = "The first name must be between 1 to 35 characters.", groups = {OnCreate.class, OnUpdate.class})
    private String firstName;

    @NotBlank(message = "Please provide a last name.", groups = {OnCreate.class})
    @Size(min = 1, max = 35, message = "The first name must be between 1 to 35 characters.", groups = {OnCreate.class, OnUpdate.class})
    private String lastName;

    @NotBlank(message = "Please provide a gender.", groups = {OnCreate.class})
    @Include(contains = "male|female|MALE|FEMALE", delimiter = "\\|", groups = {OnCreate.class, OnUpdate.class})
    private String gender;

    @NotNull(message = "Please provide a birth date.", groups = {OnCreate.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDate;

    @NotBlank(message = "Please provide a nationality.", groups = {OnCreate.class})
    @Size(min = 1, max = 35, message = "The nationality must be not blank value.", groups = {OnCreate.class, OnUpdate.class})
    private String nationality;

    @NotBlank(message = "Please provide an identity card number.", groups = {OnCreate.class})
    @Size(min = 1, max = 15, message = "The identifier number must be not blank value.", groups = {OnCreate.class, OnUpdate.class})
    private String idNumber;

    @NotNull(message = "Please provide an expiry date of the identity card. ", groups = {OnCreate.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String expireDate;

    @NotNull(message = "Please provide a type of the identity card.", groups = {OnCreate.class})
    @Include(contains = "0|1", delimiter = "\\|", groups = {OnCreate.class, OnUpdate.class})
    private Integer idType;

}
