package com.skybooking.skyflightservice.v1_0_0.ui.model.request.backoffice.offlineitinerary;

import com.skybooking.skyflightservice.exception.anotation.OfflineBookingPnrValidate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class CheckItineraryRQ {

    @NotNull
    @Length(min = 6, max = 6, message = "The PNR Code must be 6 characters")
    @OfflineBookingPnrValidate
    private String pnrCode;

}
