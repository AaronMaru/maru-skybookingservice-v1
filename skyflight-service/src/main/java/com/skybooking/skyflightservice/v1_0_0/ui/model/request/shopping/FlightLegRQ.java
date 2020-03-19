package com.skybooking.skyflightservice.v1_0_0.ui.model.request.shopping;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class FlightLegRQ {

    @NotBlank(message = "The departure is required.")
    private String departure;

    @NotBlank(message = "The arrival is required.")
    private String arrival;

    @NotNull(message = "The departure date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate date;

}
