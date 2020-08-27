package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import com.skybooking.core.validators.annotations.IsIn;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ServiceRQ extends AdditionalItemRQ {

    @NotNull(message = "The service type is required.")
    @IsIn(contains = {"REFUND", "MODIFIED"}, caseSensitive = true)
    private String type;

    @NotNull(message = "The service happened is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date happenedAt;
}
