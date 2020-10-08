package com.skybooking.skyflightservice.v1_0_0.ui.model.response.backoffice.offlineitinerary;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PassengerInfoRS {
    private String firstName;
    private String lastName;
    private String passengerType;
    private String ticketNumber;
    private BigDecimal amount;
    private String currency;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDay;
    private String gender;
    private String nationality;
    private String idType;
    private String idNumber;
}
