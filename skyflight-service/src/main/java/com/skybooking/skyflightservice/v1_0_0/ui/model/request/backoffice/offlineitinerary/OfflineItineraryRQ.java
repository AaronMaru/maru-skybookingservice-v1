package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary;

import com.skybooking.core.validators.annotations.IsIn;
import com.skybooking.skyflightservice.exception.anotation.OfflineBookingValidate;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@OfflineBookingValidate
public class OfflineItineraryRQ extends CheckItineraryRQ {

    @NotNull(message = "REQUIRE_MARKUP_AMOUNT")
    @Min(value = 0, message = "MARKUP_AMOUNT_MIN_0")
    @Max(value = 1000000, message = "MARKUP_AMOUNT_MAX_1000000")
    private BigDecimal markupAmount;

    @NotNull(message = "REQUIRE_EMPLOYEE_ID")
    private Integer employeeId;

    @NotNull(message = "REQUIRE_BOOKING_TYPE")
    @IsIn(contains = {"FLIGHT_ISSUED_TICKET_FAIL", "FLIGHT_OFFLINE_BOOKING"}, message = "BOOKING_TYPE_INVALID", caseSensitive = true)
    private String bookingType;

    @Valid
    private ContactPerson contactPerson;

}
