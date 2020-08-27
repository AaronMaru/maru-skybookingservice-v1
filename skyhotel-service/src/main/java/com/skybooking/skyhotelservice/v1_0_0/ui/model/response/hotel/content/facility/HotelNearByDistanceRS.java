package com.skybooking.skyhotelservice.v1_0_0.ui.model.response.hotel.content.facility;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
public class HotelNearByDistanceRS extends BaseHotelFacilityRS {
    private Integer distance;
}
