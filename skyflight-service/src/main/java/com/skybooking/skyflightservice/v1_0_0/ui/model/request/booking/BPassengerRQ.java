package com.skybooking.skyflightservice.v1_0_0.ui.model.request.booking;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class BPassengerRQ {

    @NotBlank(message = "Passenger's first name is required.")
    private String firstName;

    @NotBlank(message = "Passenger's last name is required.")
    private String lastName;

    @NotBlank(message = "Passenger's gender is required.")
    private String gender;

    @NotNull(message = "Passenger's birth date is required.")
    @Past(message = "Passenger's birth date must be in the past.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @NotBlank(message = "Passenger's nationality is required.")
    private String nationality;

    @NotNull(message = "Passenger's id type is required.")
    private Integer idType;

    @NotBlank(message = "Passenger's id number is required.")
    private String idNumber;

    @NotNull(message = "Passenger's expire dat is required.")
    @Future(message = "Passenger's expire date must be in the future.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date expireDate;

}
