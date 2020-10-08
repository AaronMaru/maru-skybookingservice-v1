package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.additionalservice;

import com.skybooking.core.validators.annotations.IsIn;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ServiceRQ extends AdditionalItemRQ {

    @NotNull(message = "REQUIRE_SERVICE_TYPE")
    @IsIn(contains = {"REFUND", "VOID_TICKET", "MODIFIED"}, message = "SERVICE_TYPE_INVALID", caseSensitive = true)
    private String type;

    @NotNull(message = "REQUIRE_HAPPENED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date happenedAt;
}
