package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary;

import com.skybooking.skyflightservice.exception.anotation.OfflineBookingCodeValidate;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OfflineItineraryRQ extends CheckItineraryRQ {

    @NotNull(message = "Reference code is required")
    @OfflineBookingCodeValidate
    private String referenceCode;

    @NotNull(message = "Markup percentage is required")
    @Min(value = 0)
    private double markupPercentage;

    @NotNull(message = "Employee Id is required")
    private Integer employeeId;

}
