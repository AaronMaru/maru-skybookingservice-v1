package com.skybooking.skyhotelservice.v1_0_0.ui.model.request.hotel;

import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class HotelCodeRQ {

    @NotNull(message = "The hotel code can not be null")
    @Max(1000000)
    private Integer hotelCode;

}
