package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary;

import com.skybooking.skyflightservice.exception.anotation.OfflineBookingPnrValidate;
import com.skybooking.skyflightservice.exception.anotation.ReferenceCodeValidate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class CheckItineraryRQ {

    @NotNull(message = "REQUIRE_PNR_CODE")
    @Length(min = 6, max = 6, message = "PNR_SIX_CHARACTERS")
    @OfflineBookingPnrValidate
    private String pnrCode;

    @ReferenceCodeValidate
    private String referenceCode;

}
