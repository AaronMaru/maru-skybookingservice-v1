package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.booking.checkrate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.skybooking.skyhotelservice.v1_0_0.client.model.request.booking.HolderDSRQ;
import com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.HotelRS;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CheckRateRS {

    private String code;
    private HolderDSRQ holder;
    private CheckRateHotel summary;
    private HotelRS hotel;

}
