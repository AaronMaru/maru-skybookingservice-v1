package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import com.skybooking.core.validators.annotations.IsDate;
import com.skybooking.skyhotelservice.exception.anotation.NotNullIfAnotherFieldNull;
import com.skybooking.skyhotelservice.exception.anotation.checkinoutdate.CheckinDate;
import com.skybooking.skyhotelservice.exception.anotation.checkinoutdate.CheckoutDate;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.location.DestinationRQ;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@CheckoutDate(first = "checkIn", second = "checkOut", message = "Checkout date can not be after checkin date")
@NotNullIfAnotherFieldNull(field = "destination", ifField = "geolocation")
public class AvailabilityRQ {

    @IsDate
    @CheckinDate
    private String checkIn;

    @IsDate
    private String checkOut;

    @Positive
    private int room = 1;

    @Positive
    private int adult = 1;

    @Valid
    private List<@NotNull PaxRQ> children = new ArrayList<>();

    @Valid
    private DestinationRQ destination;

    @Valid
    private GeolocationRQ geolocation;

    @Valid
    private Keywords keywords;

    private String sortBy = "SORT_RECOMMENDED";

    @Valid
    private FilterRQ filter;

    private MetaRQ meta;

}
