package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import com.skybooking.core.validators.annotations.IsDate;
import com.skybooking.skyhotelservice.exception.anotation.NotNullIfAnotherFieldNull;
import com.skybooking.skyhotelservice.exception.anotation.checkinoutdate.CheckinDate;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.request.location.DestinationRQ;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NotNullIfAnotherFieldNull(field = "destination", ifField = "geolocation")
public class HotelDetailRQ {

    @IsDate
    @CheckinDate
    private String checkIn;

    @IsDate
    private String checkOut;

    @Positive
    private int room;

    @Positive
    private int adult;

    @Valid
    private List<@NotNull PaxRQ> children;

    @NotNull
    private Integer hotelCode;

    @NotNull
    private String requestId;

    @Valid
    private DestinationRQ destination;

    @Valid
    private GeolocationRQ geolocation;

}
