package com.skybooking.skyflightservice.v1_0_0.client.distributed.ui.request.shopping;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class FlightLegRQ {

    @NotBlank(message = "The bargainfinder origin is required.")
    private String origin;

    @NotBlank(message = "The bargainfinder destination is required.")
    private String destination;

    @NotNull(message = "The departure date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date date;

}
