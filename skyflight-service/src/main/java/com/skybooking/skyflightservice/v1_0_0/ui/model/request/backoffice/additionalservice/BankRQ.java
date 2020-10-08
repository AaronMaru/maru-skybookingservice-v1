package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDate;

    @Min(value = 0, message = "BANK_FEE_MIN_0")
    @Max(value = 1000000, message = "BANK_FEE_MAX_1000000")
    private BigDecimal fee;

    @Length(max = 30, message = "NUMBER_INVALID")
    private String number;
}
