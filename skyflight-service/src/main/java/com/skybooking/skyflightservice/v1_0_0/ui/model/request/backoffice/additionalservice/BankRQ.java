package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BankRQ extends AdditionalItemRQ {

    @NotNull(message = "The received date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDate;

    @NotNull(message = "Bank fee is required")
    @Min(value = 0, message = "Bank fee must be greater or equal 0")
    @Max(value = 1000000, message = "Bank fee must be less than 1000000")
    private BigDecimal fee;
}
