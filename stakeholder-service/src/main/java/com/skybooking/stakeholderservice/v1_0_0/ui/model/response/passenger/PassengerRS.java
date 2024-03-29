package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.passenger;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class PassengerRS {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String birthDate;
    private String gender;
    private String nationality;
    private String isoCountry;
    private String idNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String expireDate;
    private int idType;

    private String passType;

}
